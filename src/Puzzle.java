/**
 * Created by Smiley on 02/03/2016.
 */
public class Puzzle
{
    /**
     * Largeur de l'image (d'apres l'ennonce, 360)
     */
    public static final int imgLarg = 360;
    /**
     * Hauteur de l'image (d'apres l'ennonce, 480)
     */
    public static final int imgHaut = 480;

    /**
     * Nombre de pieces en X (d'apres l'ennonce, 3)
     */
    public static int nbPiecesX = 3;
    /**
     * Nombre de pieces en Y (d'apres l'ennonce, 4)
     */
    public static int nbPiecesY = 4;

    /**
     * Position des grilles
     */
    public static int x1 = 10;
    public static int y1 = 10;
    public static int x2 = x1 + imgLarg + 100;
    public static int y2 = y1;

    /**
     * Pour avoir un clic unique
     */
    public static boolean clicUnique = true;
    public static Position2D clic = new Position2D();

    /**
     * Fonction de tirage d'un entier aleatoire dans [min, max]
     * @param min   entier minimum
     * @param max   entier maximum
     * @return      entier aleatoire
     */
    public static int rand(int min, int max)
    {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    /**
     * Fonction principale avec les premieres initialisations
     * @param args
     */
    public static void main(String[] args)
    {
        EcranGraphique.init(0, 0, 1280, 720, 1280, 720, "Puzzle"); // Init de la fenetre
        EcranGraphique.setClearColor(255, 255, 255);
        EcranGraphique.clear();

        PuzzleJeu pzl = new PuzzleJeu();
        initialiser(pzl, saisirImage());
        melanger(pzl);
        jouer(pzl);
    }

    /**
     * Structure Piece
     */
    static class Piece
    {
        int[][] image;
        boolean placee;
        Position2D pos;
    }

    /**
     * Structure position
     */
    static class Position2D
    {
        int x, y;
    }

    /**
     * Structure Puzzle
     */
    static class PuzzleJeu
    {
        int nbCoups;
        Piece[][] pieces;
        long temps;
        Menu mn;
        Position2D caseChoisie;
        // int tx, ty;
    }

    /**
     * Structure Menu
     */
    static class Menu
    {
        Bouton relanc;
        Bouton taille3x4;
        Bouton taille6x8;
    }

    /**
     * Structure Bouton
     */
    static class Bouton
    {
        int x, y, larg, haut;
        String label;
        boolean survol = false;
        boolean appui = false;
    }

    /**
     * Trace un bouton
     * @param bt    bouton a tracer
     */
    public static void drawBouton(Bouton bt)
    {
        // int larg = 10 * bt.label.length();
        // int haut = 25;

        if(bt.survol && !bt.appui)
            EcranGraphique.setColor(100, 0, 0);
        else if(bt.appui)
            EcranGraphique.setColor(255, 0, 0);
        else
            EcranGraphique.setColor(0, 0, 0);

        EcranGraphique.fillRect(bt.x, bt.y, bt.larg, bt.haut);
        EcranGraphique.setColor(255, 255, 255);
        EcranGraphique.drawString(bt.x + 4, bt.y + bt.haut - 5, EcranGraphique.COLABA8x13, bt.label);
    }

    /**
     * Retourne si le curseur est sur le bouton
     * @param bt   le bouton a tester
     * @return     vrai si le curseur est sur le bouton
     */
    public static boolean estSurBouton(Bouton bt)
    {
        boolean estDessus = false;

        if(EcranGraphique.getMouseX() >= bt.x && EcranGraphique.getMouseX() <= bt.x + bt.larg
                && EcranGraphique.getMouseY() >= bt.y && EcranGraphique.getMouseY() <= bt.y + bt.haut)
            estDessus = true;

        return estDessus;
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

        // pzl.pieces[2][1].placee = true;
        // pzl.pieces[1][1].placee = true;

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
                //if(pzl.pieces[i][j].placee)
                //    EcranGraphique.drawImage(x1 + (imgLarg / nbPiecesX) * i,
                //             y1 + (imgHaut / nbPiecesY) * j, pzl.pieces[i][j].image);
                // else
                if(i != pzl.caseChoisie.x || j != pzl.caseChoisie.y) // Pour ne pas afficher la case tenue
                {
                    EcranGraphique.drawImage(pzl.pieces[i][j].pos.x,
                            pzl.pieces[i][j].pos.y, pzl.pieces[i][j].image);
                }
            }
        }

        if(pzl.caseChoisie.x != -1 && pzl.caseChoisie.y != -1) // La case tenue doit etre affichee au dessus des autres
            EcranGraphique.drawImage(pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.x,
    pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.y,
    pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].image);

    // fin du jeu
    if(estReconstitue(pzl))
    {
        EcranGraphique.drawText(x2, 50, EcranGraphique.COLABA8x13, "Tu as reussi le puzzle en "
                + pzl.nbCoups + " coups !");
        EcranGraphique.drawText(x2, 70, EcranGraphique.COLABA8x13, "Clique n'importe ou pour recommencer," +
                " ou bien quitte le jeu");
    }

    drawBouton(pzl.mn.relanc);
    drawBouton(pzl.mn.taille3x4);
    drawBouton(pzl.mn.taille6x8);

    EcranGraphique.flush();
}

    /**
     * Fonction qui dit si le puzzle est fini
     * @param pzl   puzzle a verifier
     * @return      vrai si le puzzle est fini
     */
    public static boolean estReconstitue(PuzzleJeu pzl)
    {
        int i = 0;
        int j = 0;
        boolean reconstituee = true;

        /* Tu devrais lire ce que dit ton algo :
            - Tant que i est inferieur ou egal a nbPiecesX (donc i : 0,1,2,3) ET que reconstituee est faux
                     (or reconstituee est a true au depart donc on n'entre meme pas dans la boucle), alors:
                     *

        */
        /*
        while (i <= nbPiecesX &&  reconstituee == false) // Un tour de trop // Il faut tester reconstituee == true
		{
            i++; // i et j seront tjrs egaux
            j++;
			while(j <= nbPiecesY && pzl.pieces[i][j].placee == false && reconstituee == false) // Un tour de trop
			                                                               // Il faut tester reconstituee == true
			{
			b1= false;
			}


		}*/

        // CORRECTION :

        while(i < nbPiecesX && reconstituee) // Tant qu'on n'a pas trouve de case non placee
        {
            j = 0; // On remet a zero j pour compter a partir de zero a chaque tour
            while(j < nbPiecesY && reconstituee) // Tant qu'on n'a pas trouve de case non placee
            {
                if(!pzl.pieces[i][j].placee)
                {
                    reconstituee = false;
                }
                j++; // incrementation de j a chaque tour de la boucle interieure
            }
            i++; // incrementation de i a chaque tour de la premiere boucle
        }

        return reconstituee;

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
        pzl.caseChoisie = new Position2D();
        pzl.caseChoisie.x = -1;
        pzl.caseChoisie.y = -1;

        // Menu
        pzl.mn = new Menu();

        // Bouton relancer un puzzle
        pzl.mn.relanc = new Bouton();
        pzl.mn.relanc.label = "Autre puzzle";
        pzl.mn.relanc.x = x1 + 10;
        pzl.mn.relanc.y = y1 + 480 + 20;
        pzl.mn.relanc.larg = 10 * pzl.mn.relanc.label.length();
        pzl.mn.relanc.haut = 25;

        // Bouton grille de taille 3x4
        pzl.mn.taille3x4 = new Bouton();
        pzl.mn.taille3x4.label = "Grille 3x4";
        pzl.mn.taille3x4.x = pzl.mn.relanc.x;
        pzl.mn.taille3x4.y = pzl.mn.relanc.y + 35;
        pzl.mn.taille3x4.larg = 10 * pzl.mn.taille3x4.label.length();
        pzl.mn.taille3x4.haut = 25;

        // Bouton grille de taille 6x8
        pzl.mn.taille6x8 = new Bouton();
        pzl.mn.taille6x8.label = "Grille 6x8";
        pzl.mn.taille6x8.x = pzl.mn.relanc.x;
        pzl.mn.taille6x8.y = pzl.mn.taille3x4.y + 35;
        pzl.mn.taille6x8.larg = 10 * pzl.mn.taille6x8.label.length();
        pzl.mn.taille6x8.haut = 25;

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
        boolean relancer = false;

        while(!estReconstitue(pzl) && !relancer)
        {
            relancer = jouerCoup(pzl);
            pzl.nbCoups++;
        }

        if(relancer)
        {
            pzl = null;
            pzl = new PuzzleJeu();
            initialiser(pzl, saisirImage());
            melanger(pzl);
            jouer(pzl);
        }

        while(estReconstitue(pzl))
        {
            afficher(pzl);
            if(attendClic())
            {
                pzl = null;
                pzl = new PuzzleJeu();
                initialiser(pzl, saisirImage());
                melanger(pzl);
                jouer(pzl);
            }
            EcranGraphique.wait(16);
        }
    }

    /**
     * Joue un coup
     * @param pzl
     * @return     Un booleen : vrai si il faut changer de puzzle
     */
    public static boolean jouerCoup(PuzzleJeu pzl)
    {
        boolean clk = false;
        boolean finDuCoup = false;
        boolean relancer = false;

        // Case a deplacer
        // Position2D caseADepl = new Position2D();

        int x, y;

        while(!finDuCoup && !relancer)
        {
            if (attendClic()) { // Si il y a un clic
                if (clk == false && estSurPiece(pzl).x != -1 && estSurPiece(pzl).y != -1) { // Si le clic
                    // est sur une piece
                    pzl.caseChoisie = estSurPiece(pzl);

                    if(pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].placee) // Si la piece etait placee
                    {
                        pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].placee = false;
                    }

                    clk = true; // pour tenir la piece jusqu'au clic suivant
                }
                else if(clk == true) // quand on a la piece en main et qu'on clique...
                {
                    x = (clic.x - x1) / (imgLarg / nbPiecesX);
                    y = (clic.y - y1) / (imgHaut / nbPiecesY);
                    if(x >= 0 && y >= 0 && x < nbPiecesX && y < nbPiecesY)
                    {
                        // On met la piece dans sa case si elle est au dessus de la grille
                        pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.x = x * (imgLarg / nbPiecesX) + x1;
                        pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.y = y * (imgHaut / nbPiecesY) + y1;

                    }

                    if(x == pzl.caseChoisie.x && y == pzl.caseChoisie.y) // Si elle est au bon endroit
                    {
                        pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].placee = true;
                    }

                    finDuCoup = true;
                    clk = false;
                }

                if(estSurBouton(pzl.mn.relanc))
                {
                    pzl.mn.relanc.appui = true;
                    relancer = true;
                }
                else
                    pzl.mn.relanc.appui = false;

                if(estSurBouton(pzl.mn.taille3x4))
                {
                    pzl.mn.taille3x4.appui = true;
                    nbPiecesX = 3;
                    nbPiecesY = 4;
                    relancer = true;
                }
                else
                    pzl.mn.taille3x4.appui = false;

                if(estSurBouton(pzl.mn.taille6x8))
                {
                    pzl.mn.taille6x8.appui = true;
                    nbPiecesX = 6;
                    nbPiecesY = 8;
                    relancer = true;
                }
                else
                    pzl.mn.taille6x8.appui = false;
            }

            // On fait suivre le curseur par la piece
            if (clk) {
                pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.x = EcranGraphique.getMouseX() - (imgLarg / nbPiecesX)/2;
                pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.y = EcranGraphique.getMouseY() - (imgHaut / nbPiecesY)/2;
            }

            // Le bouton...
            if(estSurBouton(pzl.mn.relanc))
                pzl.mn.relanc.survol = true;
            else
                pzl.mn.relanc.survol = false;
            if(estSurBouton(pzl.mn.taille3x4))
                pzl.mn.taille3x4.survol = true;
            else
                pzl.mn.taille3x4.survol = false;
            if(estSurBouton(pzl.mn.taille6x8))
                pzl.mn.taille6x8.survol = true;
            else
                pzl.mn.taille6x8.survol = false;

            // On affiche le tout
            if(!relancer) // Pour ne pas avoir de pb dans le nb de cases a afficher
                afficher(pzl);

            EcranGraphique.wait(16);
        }
        return relancer;
    }

    /**
     * Retourne si le curseur est sur une piece ou non
     * @param pzl    le puzzle a tester
     * @return    boolean
     */
    public static Position2D estSurPiece(PuzzleJeu pzl)
    {
        boolean est = false;
        int i = 0;
        int j = 0;
        Position2D pos = new Position2D();

        while(est == false && j < nbPiecesY)
        {
            i = 0;
            while(est == false && i < nbPiecesX)
            {
                est = clic.x >= pzl.pieces[i][j].pos.x && clic.x <= pzl.pieces[i][j].pos.x + (int)(imgLarg / nbPiecesX)
                        && clic.y >= pzl.pieces[i][j].pos.y && clic.y <= pzl.pieces[i][j].pos.y
                        + (int)(imgHaut / nbPiecesY);
                // && !pzl.pieces[i][j].placee;
                if(est)
                {
                    pos.x = i;
                    pos.y = j;
                }
                else
                {
                    pos.x = -1;
                    pos.y = -1;
                }
                i++;
            }
            j++;
        }

        return pos;
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
        int[][] img;
        img = EcranGraphique.loadPNGFile("img/" + rand(1, 31) + ".png");

        // Lignes symbolisant les bords
        for(int j = 0; j < 5; j++)
        {
            for(int i = 0; i < imgLarg; i++)
            {
                img[i][j] = 0;
                img[i][imgHaut - j - 1] = 0;
            }
        }

        for(int j = 0; j < imgHaut; j++)
        {
            for(int i = 0; i < 5; i++)
            {
                img[i][j] = 0;
                img[imgLarg - i - 1][j] = 0;
            }
        }

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