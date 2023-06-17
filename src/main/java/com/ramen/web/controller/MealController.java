package com.ramen.web.controller;

import com.ramen.web.dto.MealDto;
import com.ramen.web.models.Meal;
import com.ramen.web.models.Order;
import com.ramen.web.models.UserEntity;
import com.ramen.web.repository.UserRepository;
import com.ramen.web.security.SecurityUtil;
import com.ramen.web.service.impl.MealService;
import com.ramen.web.service.impl.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RequestMapping("/meal")
@AllArgsConstructor
public class MealController {
//    @Value("${upload.path}")
//    private String path;
    private final MealService mealService;
//    private final MealMapper mealMapper;/* = Mappers.getMapper(MealMapper.class);*/
    private final OrderService orderService;
    private final UserRepository userRepository;
    private UserRepository userService;

//    public MealController(MealService mealService, /*MealMapper mealMapper,*/ OrderService orderService, UserRepository userRepository) {
//        this.mealService = mealService;
////        this.mealMapper = mealMapper;
//        this.orderService = orderService;
//        this.userRepository = userRepository;
//    }

    @GetMapping
    public String getAll(Model model) {
        List<Meal> meals = mealService.getAll();
        model.addAttribute("meals", meals);
        return "meals";
    }

    @GetMapping("/{id}")
    public String getMealById(@PathVariable Long id, Model model) {
        model.addAttribute("mealData", mealService.getMealById(id));
        return "meal";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add-to-order/{id}")
    public String addToBasket(@PathVariable Long id) {
        UserEntity user = new UserEntity();
        String email = SecurityUtil.getSessionUser();
        if(email != null) {
            user = userService.findByEmail(email);
        }
        Boolean isGood = false;
        isGood = orderService.addToOrder(user, id);
        if (isGood) {
            return "redirect:/meal";
        } else {
            return "redirect:/meal/add-to-order/" + id;
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/order")
    public String getBasket(Model model) {
//        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("cannot find userEntity"));
        UserEntity user = new UserEntity();
        String email = SecurityUtil.getSessionUser();
        if(email != null) {
            user = userService.findByEmail(email);
        }
        List<Order> orders = orderService.getOrderByUser(user);
        model.addAttribute(user);
        model.addAttribute(orders);
        return "order";
//        return new ResponseEntity<>(orderService.getOrderByUser(userEntity), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/order")
    public String payOrder() {
        UserEntity user = new UserEntity();
        String email = SecurityUtil.getSessionUser();
        if(email != null) {
            user = userService.findByEmail(email);
        }
        orderService.payOrder(user);
        return "redirect:/meal/order";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete-from-order/{mealId}")
    public String deleteFromBasket(/*@AuthenticationPrincipal UserEntity userEntity*/@PathVariable Long mealId) {
        orderService.deleteFromOrderById(mealId);
        return "redirect:/meal/order";
    }

    @DeleteMapping("/order")
    public String plug() {  // просто заглушка для перенаправления
        return "redirect:/meal/order";
    }

    @GetMapping("/admin/get-image/{name}")
    @ResponseBody
    public byte[] getMealImage(@PathVariable String name) throws IOException {
        return Files.readAllBytes(mealService.getMealImage(name).toPath());
    }

    @PostMapping("/admin/create-meal")
    public ResponseEntity<MealDto> createMeal(@RequestBody MealDto mealDto) {
        return new ResponseEntity<>(mealService.createMeal(mealDto), HttpStatus.CREATED);
    }

    @GetMapping("/admin/create-meal-page")
    public String createMealPageWithImg(Model model) {

        return "create-meal-page";
    }


    @PostMapping("/admin/create-meal-page")
    public String createMealPageWithImg(@RequestParam String name,
                                  @RequestParam String description,
                                  @RequestParam float price,
                                  @RequestParam MultipartFile img
    ) throws NoSuchAlgorithmException {
//        MealDTO mealDTO = new MealDTO(name, price, description);
        Meal meal = mealService.createMeal(name, price, description);
        if(meal != null) {
            mealService.addMeal(meal, img);
        } else {
            return "redirect:/";
        }
        return "redirect:/meal";
    }
}
