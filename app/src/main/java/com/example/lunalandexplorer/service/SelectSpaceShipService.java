package com.example.lunalandexplorer.service;

public class SelectSpaceShipService {

    private  static int spaceshipSelected;



    public static int getSpaceshipSelected(){
        return  spaceshipSelected;
    }

    public static void selectSpaceShip(int spaceshipSelected){
        SelectSpaceShipService.spaceshipSelected = spaceshipSelected;
    }

}
