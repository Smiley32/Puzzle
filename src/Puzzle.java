/**
 * Created by Smiley on 02/03/2016.
 */
class Puzzle
{
    // Hauteur et largeur de l'image (d'apres l'ennonce, 360*480)
    public static final int imgLarg = 360;
    public static final int imgHaut = 480;

    // Nombre de pieces (d'apres l'ennonce, 3*4)
    public static final int nbPiecesX = 3;
    public static final int nbPiecesY = 4;

    // Position des grilles
    public static int x1 = 10;
    public static int y1 = 10;
    public static int x2 = x1 + imgLarg + 100;
    public static int y2 = y1;

    // Pour avoir un clic unique
    public static boolean clicUnique = true;
    public static Position2D clic = new Position2D();

    public static int rand(int min, int max) // fct aleatoire
    {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public static void main(String[] args)
    {
        E.ln("Juste une fenetre"); // Juste un texte en console

        EcranGraphique.init(50, 50, 1280, 720, 1280, 720, "Puzzle"); // Init de la fenetre
        EcranGraphique.setClearColor(255, 255, 255);
        EcranGraphique.clear();

        PuzzleJeu pzl = new PuzzleJeu();
        initialiser(pzl, saisirImage());
        melanger(pzl);
        jouer(pzl);
    }
    
    static class Piece
    {
        int[][] image;
        boolean placee;
        Position2D pos;
    }

    static class Position2D
    {
        int x, y;
    }

    static class PuzzleJeu
    {
        int nbCoups;
        Piece[][] pieces;
        long temps;
        // int tx, ty;
    }

    /**
     * Affichage du puzzle
     * @param pzl  puzzle a afficher
     */
    public static void afficher(PuzzleJeu pzl)
    {
        /*
        Il va y avoir deux parties : le puzzle a faire a gauche,
        les pieces a placer a droite. Les pieces sont a droite
        dans la position indiquee dans Piece si elles ont le
        booleen placee a faux. s'il est a vrai, la piece ne doit
        plus se trouver a droite...
         */

        // Un ecran blanc...
        EcranGraphique.clear();

        /*int x1 = 10;
        int y1 = 10;
        int x2 = x1 + imgLarg + 100;
        int y2 = y1;*/

        pzl.pieces[2][1].placee = true;
        pzl.pieces[1][1].placee = true;

        // Affichage d'une grille
        EcranGraphique.setColor(0, 0, 0);
        for(int i = 0; i <= nbPiecesX; i++)
        {
            EcranGraphique.drawLine(x1 + i*(imgLarg / nbPiecesX), y1, x1 + i*(imgLarg / nbPiecesX), y1 + imgHaut);
        }
        for(int i = 0; i <= nbPiecesY; i++)
        {
            EcranGraphique.drawLine(x1, y1 + i*(imgHaut / nbPiecesY), x1 + imgLarg, y1 + i*(imgHaut / nbPiecesY));
        }

        // Affichage des pieces
        for(int j = 0; j < nbPiecesY; j++)
        {
            for(int i = 0; i < nbPiecesX; i++)
            {
                if(pzl.pieces[i][j].placee)
                    EcranGraphique.drawImage(x1 + (imgLarg / nbPiecesX) * i,
                             y1 + (imgHaut / nbPiecesY) * j, pzl.pieces[i][j].image);
                else
                    EcranGraphique.drawImage(pzl.pieces[i][j].pos.x,
                            pzl.pieces[i][j].pos.y, pzl.pieces[i][j].image);
            }
        }

        EcranGraphique.flush();
    }

    /**
     * Fonction qui dit si le puzzle est fini
     * @param pzl   puzzle a verifier
     * @return      vrai si le puzzle est fini
     */
    public static boolean estReconstitue(PuzzleJeu pzl)
    {
		int j = 0, i = 0;
		boolean b1 = true;

        /*ERREURS :
            Faire un do while te fait faire un tour de trop car il teste la condition a la fin (j'ai corrige)
            Ensuite, ton j n'augmente que si tu entre dans la boucle.
            De plus, tu as perdu l'interet du tant que car tu ne lui dit pas de s'arreter quand b1 = false
            Enfin, quand tu incremente j dans ton deuxieme while, tu l'incremente meme si il est egal (ou depasse
                                                                                                            nbPiecesY)

            AIDE :
                dans tes tant que, tu dois avoir deux conditions : ne pas depasser les cases max,
                                                                et arreter si b1 = false.

         */

		do
		{
			while(pzl.pieces[i][j].placee == false)
			{
			j++;
			b1= false;
			}

		i=i+1;
		} while (i <= nbPiecesX);

		return b1;

	}

    

    /**
     * Initialisation du puzzle
     * @param pzl   puzzle a initialiser
     * @param image image a malanger
     */
    public static void initialiser(PuzzleJeu pzl, int[][] image)
    {
        pzl.temps = 0;
        pzl.nbCoups = 0;
        pzl.pieces = new Piece[nbPiecesX][nbPiecesY];

        // Declaration de toutes les cases
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
                        pzl.pieces[l][k].pos.y = -1;
                        pzl.pieces[l][k].pos.x = -1;
                        pzl.pieces[l][k].placee = false;
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
        boolean continuer = true;


        while(continuer)
        {
            jouerCoup(pzl);

        }
    }

    /**
     * Joue un coup
     * @param pzl   puzzle
     */
    public static void jouerCoup(PuzzleJeu pzl)
    {
        boolean clk = false;

        if(attendClic())
        {
            if(clk == false );
        }

    }

    public static void surCaseNonPlacee()
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
                pzl.pieces[i][j].pos.x = (int)(positions[j*(nbPiecesX)+i] % nbPiecesX);
                pzl.pieces[i][j].pos.x = x2 + (imgLarg / nbPiecesX + 5) * pzl.pieces[i][j].pos.x;
                pzl.pieces[i][j].pos.y = (int)(positions[j*(nbPiecesX)+i] / nbPiecesX);
                pzl.pieces[i][j].pos.y = y2 + (imgHaut / nbPiecesY + 5) * pzl.pieces[i][j].pos.y;
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
    public static boolean attendClic()
    {
        // Position2D clic = new Position2D();

        boolean clique = false;

        if(EcranGraphique.getMouseState() == 0)
        {
            clic.x = -1;
            clic.y = -1;
            clicUnique = true;
            clique = false;
        }

        if(EcranGraphique.getMouseState() == 1 && EcranGraphique.getMouseButton() == 1)
        {
            if(clicUnique)
            {
                clic.x = EcranGraphique.getMouseX();
                clic.y = EcranGraphique.getMouseY();
                E.ln(clic.x + " - " + clic.y);
                clicUnique = false;
                clique = true;
            }
        }

        return clique;
    }
}