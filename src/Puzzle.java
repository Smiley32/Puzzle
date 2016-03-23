/**
 * Created by Smiley on 02/03/2016.
 */
class Puzzle
{
    // Hauteur et largeur de l'image (d'apres l'ennonce, 360*480)
    public static int imgLarg = 360;
    public static int imgHaut = 480;

    public static int rand(int min, int max) // fct aleatoire
    {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public static void main(String[] args)
    {
        E.ln("Juste une fenêtre"); // Juste un texte en console

        EcranGraphique.init(50, 50, 669, 552, 640, 480, "Puzzle"); // Init de la fenetre
        EcranGraphique.setClearColor(0, 255, 255);
        EcranGraphique.clear();
    }
    
    static class Piece
    {
        int[][] image;
        boolean placee;
        Position2D pos;
    }

    static class Position2D
    {
        int colonne, ligne;
    }

    static class PuzzleJeu
    {
        int nbCoups;
        Piece[][] pieces;
        long temps;
        int tx, ty;
    }

    /**
     * Affichage du puzzle
     * @param pzl  puzzle à afficher
     */
    public static void afficher(PuzzleJeu pzl)
    {

    }

    /**
     * Fonction qui dit si le puzzle est fini
     * @param pzl   puzzle à vérifier
     * @return      vrai si le puzzle est fini
     */
    public static boolean estReconstitue(PuzzleJeu pzl)
    {
        boolean bl = false;

        return bl;
    }

    /**
     * Initialisation du puzzle
     * @param pzl   puzzle à initialiser
     * @param image image à mélanger
     */
    public static void initialiser(PuzzleJeu pzl, int[][] image)
    {

    }

    /**
     * Jeu...
     * @param pzl  puzzle
     */
    public static void jouer(PuzzleJeu pzl)
    {

    }

    /**
     * Joue un coup
     * @param pzl   puzzle
     */
    public static void jouerCoup(PuzzleJeu pzl)
    {

    }

    /**
     * Melange un puzzle
     * @param pzl
     */
    public static void melanger(PuzzleJeu pzl)
    {

    }

    /**
     * Saisie d'une image
     * @return  l'image (tableau d'entiers)
     */
    public static int[][] saisirImage()
    {
        int[][] img = new int[imgHaut][imgLarg];

        return img;
    }

    /**
     * Retourne la position du clic
     * @return   la position du clic
     */
    public static Position2D attendClic()
    {
        Position2D clic = new Position2D();

        return clic;
    }
}
