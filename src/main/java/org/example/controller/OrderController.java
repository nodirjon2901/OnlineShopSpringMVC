package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.dto.order.OrderCreateDto;
import org.example.domain.dto.order.OrderReadDto;
import org.example.domain.entity.order.OrderEntity;
import org.example.domain.entity.order.OrderStatus;
import org.example.service.order.OrderService;
import org.example.service.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final ProductService productService;

    @GetMapping(value = "/all")
    public String allOrders(Model model){
        model.addAttribute("myOrders", orderService.findByUserId(AuthController.currentUser.getId()));
        model.addAttribute("findProduct", productService.productById());
        return "order-list";
    }

    @GetMapping(value = "/{productId}")
    public String orderPanel(@PathVariable(value = "productId") UUID productId,
                             Model model){
        model.addAttribute("product", productService.getById(productId));
        return "order-product";
    }

    @GetMapping(value = "/delete/{deleteId}")
    public String deleteOrder(@PathVariable(value = "deleteId") UUID orderId,
                              Model model){
        orderService.delete(orderId);
        model.addAttribute("myOrders", orderService.findByUserId(AuthController.currentUser.getId()));
        model.addAttribute("findProduct", productService.productById());
        return "order-list";
    }

    @GetMapping(value = "/deleteAll")
    public String deleteAll(Model model){
        orderService.deleteAllMyOrders(AuthController.currentUser.getId());
        model.addAttribute("myOrders", orderService.findByUserId(AuthController.currentUser.getId()));
        model.addAttribute("findProduct", productService.productById());
        return "order-list";
    }

    @GetMapping(value = "/edit/{editId}")
    public String edit(@PathVariable(value = "editId") UUID editID,
                       Model model){
        model.addAttribute("order", orderService.getById(editID));
        model.addAttribute("orderedProduct", productService.productById().get(orderService.getById(editID).getProductId()));
        return "order-edit";
    }

    @GetMapping(value = "/manage")
    public String manageOrders(Model model){
        model.addAttribute("orders", orderService.findBySeller(AuthController.currentUser.getEmail()));
        model.addAttribute("findProduct", productService.productById());
        return "order-manage";
    }

    @PostMapping(value = "/edit")
    public String editPost(@RequestParam(name = "quantity") int amount,
                           @RequestParam(name = "orderId") UUID orderId,
                           Model model){
        orderService.update(orderService.getById(orderId), amount);
        model.addAttribute("order", orderService.getById(orderId));
        model.addAttribute("orderedProduct", productService.productById().get(orderService.getById(orderId).getProductId()));
        return "order-edit";
    }

    @PostMapping(value = "/add")
    public String order(@RequestParam(name = "productId") UUID productId,
                        @RequestParam(name = "quantity") int amount,
                        Model model){
        OrderCreateDto order = new OrderCreateDto(AuthController.currentUser.getId(), productId, amount, OrderStatus.PROCESSING);
        model.addAttribute("message", orderService.create(order).getMessage());
        model.addAttribute("product", productService.getById(productId));
        return "order-product";
    }

    @PostMapping(value = "/update-status")
    public String updateStatus(@RequestParam(name = "orderId") UUID orderId,
                        @RequestParam(name = "status") OrderStatus status,
                        Model model){
        orderService.updateStatus(orderService.getById(orderId), status);
        model.addAttribute("orders", orderService.findBySeller(AuthController.currentUser.getEmail()));
        model.addAttribute("findProduct", productService.productById());
        return "order-manage";
    }

}
