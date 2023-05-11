package com.ramen.web.service.impl;

import com.ramen.web.dto.MealDto;
import com.ramen.web.models.Meal;
import com.ramen.web.repository.MealRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
public class MealService {
    private final MealRepository mealRepository;
    @Value("${upload.path}")
    private String uploadPath;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meal createMeal(String name, float price, String description) {
        Meal meal = new Meal();
        meal.setName(name);
        meal.setDescription(description);
        meal.setPrice(price);
        mealRepository.save(meal);
        return meal;
    }

    public MealDto createMeal(MealDto mealDTO) {
        Meal meal = new Meal();
        meal.setName(mealDTO.getName());
        meal.setPrice(mealDTO.getPrice());
        meal.setDescription(mealDTO.getDescription());
        Meal newMeal = mealRepository.save(meal);

        MealDto mealResponse = new MealDto();
        mealResponse.setName(newMeal.getName());
        mealResponse.setPrice(newMeal.getPrice());
        mealResponse.setDescription(newMeal.getDescription());
        return mealResponse;
    }

    public boolean addMeal(Meal meal, MultipartFile img) throws NoSuchAlgorithmException {
        if(mealRepository.existsByName(meal.getName())) {
            return false;
        }
        mealRepository.save(meal);
        if(!img.isEmpty()) {
            try {
                img.transferTo(new File(uploadPath + "/" + meal.getName()
                        + "." + Objects.requireNonNull(img.getOriginalFilename()).split("\\.")[1]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public Meal getMealById(Long mealId) {
        return mealRepository.findById(mealId).orElseThrow(() -> new RuntimeException("cannot find meal"));
    }

    public List<Meal> getAll() {
        return mealRepository.findAll();
    }

    public void deleteMealById(Long mealId) {
        mealRepository.deleteById(mealId);
    }

    public File getMealImage(String mealName) {
        File f = new File(uploadPath);
        File[] matchingFiles = f.listFiles((dir, name) -> name.startsWith(mealName));

        assert matchingFiles != null;
        return matchingFiles[0];
    }

    public void saveChanges(Meal meal, MultipartFile img) {
        Meal mealInDb = mealRepository.findById(meal.getId()).orElseThrow(() -> new RuntimeException("cannot find meal"));
        if (!img.isEmpty()) {
            try {
                File prevImg = getMealImage(mealInDb.getName());
                prevImg.delete();
                img.transferTo(new File(uploadPath + "/" + meal.getName()
                        + "." + Objects.requireNonNull(img.getOriginalFilename()).split("\\.")[1]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File prevImg = getMealImage(mealInDb.getName());
            try {
                Files.move(prevImg.toPath(), prevImg.toPath().resolveSibling(meal.getName() + "." + prevImg.getName().split("\\.")[1]));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        mealInDb.setName(meal.getName());
        mealInDb.setPrice(meal.getPrice());
        mealInDb.setDescription(meal.getDescription());

        mealRepository.save(mealInDb);
    }

}
