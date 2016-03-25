/**
 * Created by Smiley on 02/03/2016.
 */
class Puzzle
{
    // Hauteur et largeur de l'image (d'apres l'ennonce, 360*480)
    public static int imgLarg = 360;
    public static int imgHaut = 480;

    // Nombre de pieces (d'apres l'ennonce, 3*4)
    public static int nbPiecesX = 3;
    public static int nbPiecesY = 4;

    public static int rand(int min, int max) // fct aleatoire
    {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public static void main(String[] args)
    {
        E.ln("Juste une fenêtre"); // Juste un texte en console

        EcranGraphique.init(50, 50, 669, 552, 640, 480, "Puzzle"); // Init de la fenetre
        EcranGraphique.setClearColor(255, 255, 255);
        EcranGraphique.clear();


        PuzzleJeu pzl = new PuzzleJeu();
        initialiser(pzl, saisirImage());
        EcranGraphique.drawImage(0, 0, pzl.pieces[0][1].image);
        melanger(pzl);
        E.ln("X : " + pzl.pieces[0][1].pos.colonne + " ; Y : " + pzl.pieces[0][1].pos.ligne);
        EcranGraphique.flush();
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
                boolean bl = true;
		for (int i = 0; i < nbPiecesX; i++)
		{				
			for(int j =0; j < nbPiecesY; j++)
			{
				if( pzl.pieces[i][j].placee == false)    
				{
					bl = false;
				}
			}			
			
		}
        return bl;
        
        return bl;
    }

    /**
     * Initialisation du puzzle
     * @param pzl   puzzle à initialiser
     * @param image image à mélanger
     */
    public static void initialiser(PuzzleJeu pzl, int[][] image)
    {
        pzl.temps = 0;
        pzl.nbCoups = 0;
        pzl.pieces = new Piece[nbPiecesX][nbPiecesY];

        // Déclaration de toutes les cases
        for(int j = 0; j < nbPiecesY; j++)
        {
            for(int i = 0; i < nbPiecesX; i++)
            {
                pzl.pieces[i][j] = new Piece();
                pzl.pieces[i][j].image = new int[imgLarg / nbPiecesX][imgHaut / nbPiecesY];
                pzl.pieces[i][j].pos = new Position2D();
            }
        }

        // On decoupe l'image en pieces
        for(int k = 0; k < nbPiecesY; k++)
        {
            for (int l = 0; l < nbPiecesX; l++) // On parcourt chaque case de la grille
            {
                for(int j = 0; j < imgHaut / nbPiecesY; j++) // On copie chaque pixel de l'image dans la case
                {
                    for(int i = 0; i < imgLarg / nbPiecesX; i++)
                    {
                        pzl.pieces[l][k].image[i][j] = image[l * (imgLarg / nbPiecesX) + i]
                                                            [k * (imgHaut / nbPiecesY) + j];
                        pzl.pieces[l][k].pos.ligne = l;
                        pzl.pieces[l][k].pos.colonne = k;
                    }
                }
            }
        }
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
        int nbCases = nbPiecesX * nbPiecesY;
        int positions[] = new int[nbCases];

        // On remplit position
        for(int i = 0; i < nbCases; i++)
        {
            positions[i] = i;
        }

        // On melange ce tableau : Melange de Fisher-Yates
        int k;
        int echangeur;
        for(int i = nbCases - 1; i >= 1; i--)
        {
            k = rand(0, i);
            echangeur = positions[i];
            positions[i] = positions[k];
            positions[k] = echangeur;
        }

        // On change les positions des cases
        for(int j = 0; j < nbPiecesY; j++)
        {
            for(int i = 0; i < nbPiecesX; i++)
            {
                pzl.pieces[i][j].pos.colonne = positions[j*(nbPiecesX)+i] % nbPiecesX;
                pzl.pieces[i][j].pos.ligne = (int)(positions[j*(nbPiecesX)+i] / nbPiecesX);
            }
        }
    }

    /**
     * Saisie d'une image
     * @return  l'image (tableau d'entiers)
     */
    public static int[][] saisirImage()
    {
        int[][] img = new int[imgHaut][imgLarg];
        img = EcranGraphique.loadPNGFile("image.png");
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
