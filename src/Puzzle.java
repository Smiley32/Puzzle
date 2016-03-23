/**
 * Created by Smiley on 02/03/2016.
 */
class Puzzle
{
    public static int rand(int min, int max) // fct aleatoire
    {
        return (int)(Math.random() * (max - min + 1)) + min;
    }
    
    public static int nbCasesX = 5;
    public static int nbCasesY = 6;
    
    /**
     *  Type agrege qui va contenir l'ensemle du jeu :
     *   - deux grilles
     */
    static class Puzzles
    {
        Image[][] grilleMelange;
        Image[][] grille;
    }
    
    /**
     *  Type agrege Image : contient une image
     */
    static class Image
    {
        int[][] img;
        // int larg, haut;
    }

    public static void main(String[] args)
    {
        E.ln("Juste une fenêtre"); // Juste un texte en console

        EcranGraphique.init(50, 50, 669, 552, 640, 480, "Puzzle"); // Init de la fenetre
        EcranGraphique.setClearColor(0, 255, 255);
        EcranGraphique.clear();
        
        init();
        render();
        
        Puzzles jeu = new Puzzles();
    }
    
    public static Image chargerImage(String path)
    {
        Image pic = new Image();
        return pic;
    }
    
    public static Image[][] decouperImage(Image image)
    {
        
        
        return grille;
    }
    
    public static void init()
    {
        
    }
    
    public static void update()
    {
        
    }
    
    public static void render()
    {
        // Effacement de l'ecran
        EcranGraphique.setClearColor(255, 255, 255);
        EcranGraphique.clear();
        
        EcranGraphique.setColor(255, 0, 0);
        EcranGraphique.fillRect(10, 20, 50, 50);
    }
}
