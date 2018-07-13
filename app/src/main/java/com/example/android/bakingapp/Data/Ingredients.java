package com.example.android.bakingapp.Data;

public class Ingredients
{
    private String measure;

    private String ingredient;

    private String quantity;

    public String getMeasure ()
    {
        return measure;
    }

    public void setMeasure (String measure)
    {
        this.measure = measure;
    }

    public String getIngredient ()
    {
        return ingredient;
    }

    public void setIngredient (String ingredient)
    {
        this.ingredient = ingredient;
    }

    public String getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (String quantity)
    {
        this.quantity = quantity;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [measure = "+measure+", ingredient = "+ingredient+", quantity = "+quantity+"]";
    }
}
