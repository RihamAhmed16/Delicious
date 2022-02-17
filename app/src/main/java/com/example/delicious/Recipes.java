package com.example.delicious;

public class Recipes {
    private String RecipeName,RecipeNo,RecipeImg=null,SubcategoryNo,clientNo=null,CategoryNo;
    private float ratingBar=0;

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

    public float getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(float ratingBar) {
        this.ratingBar = ratingBar;
    }
}
