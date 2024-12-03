package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.dto.product.ProductReadDto;
import org.example.domain.dto.user.UserCreateDto;
import org.example.domain.dto.user.UserReadDto;
import org.example.domain.entity.user.UserRole;
import org.example.service.product.ProductService;
import org.example.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class AuthController {

    public static UserReadDto currentUser;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public String registerGet(Model model){
        return "register";
    }

    @PostMapping
    public String register(@ModelAttribute UserCreateDto createDto,
                           Model model){
        currentUser = userService.create(createDto).getData();
        if(currentUser == null){
            model.addAttribute("message", userService.create(createDto).getMessage());
            return "register";
        }
        if (currentUser.getRole().equals(UserRole.SELLER)){
            return "seller-menu";
        }
        model.addAttribute("allProducts", productService.getAll());
        return "categories";
    }
    @GetMapping(value = "/login")
    public String loginGet(){
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam(value = "email") String email,
                        @RequestParam(value = "password") String password,
                        Model model){
       if(userService.signIn(email, password).getStatus() == 400){
           model.addAttribute("message", userService.signIn(email, password).getMessage());
           return "login";
       }
       currentUser = userService.signIn(email, password).getData();
        if (currentUser.getRole().equals(UserRole.CUSTOMER)){
            model.addAttribute("allProducts", productService.getAll());
            return "categories";
        }else {
            model.addAttribute("myProducts", productService.findMyProducts(currentUser.getEmail()));
            return "seller-menu";
        }
    }
}
