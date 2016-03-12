/**
 * Created by Smiley on 02/03/2016.
 */
public class Puzzle
{
    public static class Position // position (pour la souris pour l'instant)
    {
        int x = 0;
        int y = 0;
    }

    public static class Inputs // type agrege contenant les entres de l'utilisateur
    {
        // boolean[] key = new boolean[256];
        boolean quitter = false;
        Position souris = new Position();
        // Position sourisRel;
        // boolean[] mouseButtons = new boolean[8];
        char key = 0;
        int mouseKey = 0;
    }

    public static void updateEvents(Inputs in) // simplifier la gestion des events
    {
        in.souris.x = EcranGraphique.getMouseX();
        in.souris.y = EcranGraphique.getMouseY();

        if(EcranGraphique.getKey() == (char)(27))
            in.quitter = true;

        in.key = EcranGraphique.getKey();

        in.mouseKey = EcranGraphique.getMouseButton();
    }

    public static int rand(int min, int max) // fct aleatoire
    {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public static void main(String[] args)
    {
        E.ln("Juste une fenêtre"); // Juste un texte en console

        EcranGraphique.init(50, 50, 669, 552, 640, 480, "Puzzle"); // Init de la fenetre !ne pas changer les valeurs!
        EcranGraphique.setClearColor(255, 255, 255);
        EcranGraphique.clear(); // Pour un beau fond blanc
        Inputs in = new Inputs(); // pour gerer les touches appuyees

        while(!in.quitter)
        {
            updateEvents(in); // mise a jour des touches appuyees

            if(in.key != 0)
                E.ln("Tu as appuyé la touche '" + in.key + "' !"); // Si on appuie sur une touche

            EcranGraphique.wait(10); // 100 images par secondes
            // EcranGraphique.clear(); // Si on veut effacer l'ecran avant de reafficher le cercle
            EcranGraphique.setColor(rand(0, 255), rand(0, 255), rand(0, 255)); // Couleur de trace aleatoire
            EcranGraphique.drawCircle(rand(25, EcranGraphique.getBufferWidth() - 25), rand(25, EcranGraphique.getBufferHeight() - 25), rand(50, 100));
            EcranGraphique.flush(); // Affichage a l'ecran
        }

        EcranGraphique.exit();
    }
}
