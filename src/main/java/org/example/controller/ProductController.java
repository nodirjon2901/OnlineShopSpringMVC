package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.dto.product.ProductCreateDto;
import org.example.domain.dto.user.UserReadDto;
import org.example.domain.entity.product.ProductCategory;
import org.example.domain.entity.product.ProductEntity;
import org.example.domain.entity.user.UserEntity;
import org.example.service.product.ProductService;
import org.example.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public String allProducts(Model model){
        model.addAttribute("allProducts", productService.getAll());
        return "categories";
    }
    @GetMapping(value = "/{category}")
    public String userMenu(@PathVariable(value = "category") String strCategory,
                           Model model){
        ProductCategory category = ProductCategory.valueOf(strCategory);
        if (category.equals(ProductCategory.PHONE)){
            model.addAttribute("categoryProducts", productService.findByCategory(category));
            return "phones";
        }
        if (category.equals(ProductCategory.LAPTOP)){
            model.addAttribute("categoryProducts", productService.findByCategory(category));
            return "laptops";
        }
        model.addAttribute("categoryProducts", productService.findByCategory(ProductCategory.TV));
        return "tv";
    }
    @PostMapping(value = "/create-products")
    public String createProduct(@ModelAttribute ProductCreateDto productCreateDto,
                                Model model){
        productCreateDto.setMaker(AuthController.currentUser.getEmail());
        if(productService.create(productCreateDto).getStatus() == 400){
            model.addAttribute("message", productService.create(productCreateDto).getMessage());
        }
        model.addAttribute("myProducts", productService.findMyProducts(AuthController.currentUser.getEmail()));
        return "seller-menu";
    }
    @GetMapping(value = "/product-edit/{productId}")
    public String details(@PathVariable(value = "productId") UUID productId,
                          Model model){
        model.addAttribute("product", productService.getById(productId));
        return "product-edit";
    }

    @PostMapping(value = "/edit")
    public String edit(@ModelAttribute ProductEntity product,
                       @RequestParam(name = "id") UUID productId,
                       Model model) {
        productService.update(product);
        model.addAttribute("product", productService.getById(productId));
        return "product-edit";
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestParam(value = "deleteId") UUID uuid,
                         Model model){
        productService.delete(uuid);
        model.addAttribute("myProducts", productService.findMyProducts(AuthController.currentUser.getEmail()));
        return "seller-menu";
    }
    @GetMapping(value = "/seller-menu")
    public String sellerMenu(Model model){
        model.addAttribute("myProducts", productService.findMyProducts(AuthController.currentUser.getEmail()));
        return "seller-menu";
    }
}
