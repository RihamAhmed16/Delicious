package com.example.delicious;

public class Recipe {
    private String RecipeName,RecipeNo,RecipeImg,SubcategoryNo,clientNo=null,CategoryNo;


    public String getRecipeName() {
        return RecipeName;
    }

    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }

    public String getRecipeNo() {
        return RecipeNo;
    }

    public void setRecipeNo(String recipeNo) {
        RecipeNo = recipeNo;
    }

    public String getRecipeImg() {
        return RecipeImg;
    }

    public void setRecipeImg(String recipeImg) {
        RecipeImg = recipeImg;
    }


    public String getSubcategoryNo() {
        return SubcategoryNo;
    }

    public void setSubcategoryNo(String subcategoryNo) {
        SubcategoryNo = subcategoryNo;
    }

    public String getClientNo() {
        return clientNo;
    }

    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }

    public String getCategoryNo() {
        return CategoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        CategoryNo = categoryNo;
    }
}
