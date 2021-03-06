/**
 * Projet n°2 (Puzzle) - Jeannin Emile, Mottet Theo - I4 CMI
 */
public class PuzzleOLD
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
     * Nombre de pieces en X (d'apres l'ennonce, 3 ; peut passer a 6)
     */
    public static int nbPiecesX = 3;
    /**
     * Nombre de pieces en Y (d'apres l'ennonce, 4 ; peut passer a 8)
     */
    public static int nbPiecesY = 4;
    
    /**
     * Indique si la rotation des pieces est activee ou non
     */
    public static boolean rotation = true;

    /**
     * Position de la grille en X
     */
    public static int x1 = 10;
    /**
     * Position de la grille en Y
     */
    public static int y1 = 10;
    /**
     * Position du debut d'afichage des pieces au debut d'un nouveau jeu en X
     */
    public static int x2 = x1 + imgLarg + 100;
    /**
     * Position du debut d'afichage des pieces au debut d'un nouveau jeu en Y
     */
    public static int y2 = y1;

    /**
     * Pour avoir un clic unique : on utilise un booleen qui se souvient si un clic a ete fait ou non.
     * Ce booleen prend donc la valeur true lors d'un clic (qu'importe la longueur du clic)
     * et prend la valeur false lors du relachement de ce clic grace a la fonction attendClic()
     */
    // public static boolean clicUnique = true;

    /**
     * Position du clic dans la fenetre. Cette position est mise a jour avec la fonction attendClic()
     */
    // public static Position2D clic = new Position2D();

    /**
     * Souris qui est utilisee tout au long du jeu
     */
    public static Souris souris = new Souris();

    /**
     * Fonction qui tire un entier aleatoire dans [min, max]
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
     * @param args  tableau de chaines de caracteres qui sont les arguments envoyes a l'application
     */
    public static void main(String[] args)
    {
        // Initialisation de la fenetre
        EcranGraphique.init(0, 0, 1280, 720, 1280, 720, "Puzzle"); // Init de la fenetre
        // Definition de la couleur avec laquelle on efface l'ecran
        EcranGraphique.setClearColor(255, 255, 255);
        // Premier effacage
        EcranGraphique.clear();

        // Declaration de la variable contenant l'essentiel du jeu
        PuzzleJeu pzl = new PuzzleJeu();
        // Initialisation de cette variable
        initialiser(pzl, saisirImage());
        // Melange du puzzle
        melanger(pzl);
        // On peut jouer !
        jouer(pzl);
    }

    /**
     * Type agrege Souris qui represente la souris, son curseur et sa position, si il y a clic ou non
     */
    static class Souris
    {
        /** Position du curseur **/
        Position2D pos;
        /** Booleen qui indique si le clic droit est appuye **/
        boolean clicDroit = false;
        /** Boolean qui indique si le clic gauche est appuye **/
        boolean clicGauche = false;
    }

    /**
     * Type agrege Piece qui represente une des pieces du puzzle
     */
    static class Piece
    {
        /** Tableau d'entiers qui est l'image contenue dans la piece **/
        int[][] image;
        /** Si la piece est placee correctement ou non **/
        boolean placee;
        /** Position de la piece dans la fenetre : la piece bouge librement dans la fenetre **/
        Position2D pos;
        /** Rotation de la piece : 0, 90, 180 ou 270 **/
        int rotation;
    }

    /**
     * Type agrege position qui permet de stocker les coordonnees d'une position (ou un point)
     */
    static class Position2D
    {
        /** Coordonnee en X d'une position / un point **/
        int x;
        /** Coordonnee en Y d'une position / un point **/
        int y;
    }

    /**
     * Type agrege Puzzle : represente un puzzle et le necessaire pour y jouer
     */
    static class PuzzleJeu
    {
        /** Nombre de coups que l'utilisateur a execute **/
        int nbCoups;
        /** Tableau de pieces qui sera de la taille nbPiecesX x nbPiecesY **/
        Piece[][] pieces;
        /** Temps ecoule depuis le debut de la partie **/
        long temps;
        /** Menu a afficher avec le puzzle **/
        Menu mn;
        /** Position de la case actuellement choisie par l'utilisateur. Est a (-1, -1) si il n'y en a pas **/
        Position2D caseChoisie;
        /** Grille du placement des cases. 0 : aucune case; 1 : Contient une case bien placee; 2 : contient une case mal placee **/
        int[][] placements;
        // int tx, ty;
    }

    /**
     * Type agrege Menu : permet de symboliser un menu (donc plusieurs boutons)
     */
    static class Menu
    {
        /** Bouton relancer : essayer un autre puzzle **/
        Bouton relanc;
        /** Bouton pour choisir le nombre de pieces : 3x4 **/
        Bouton taille3x4;
        /** Bouton pour choisir le nombre de pieces : 6x8 **/
        Bouton taille6x8;
        /** Bouton pour activer la rotation des pieces **/
        Bouton activRotation;
    }

    /**
     * Type agrege Bouton : permet de symboliser un bouton, qu'on pourra survoler et cliquer, contenant un texte
     */
    static class Bouton
    {
        /** Position en X du bouton dans la fenetre **/
        int x;
        /** Position en Y du bouton dans la fenetre **/
        int y;
        /** Largeur du bouton **/
        int larg;
        /** Longueur du bouton **/
        int haut;
        /** Texte a afficher sur le bouton **/
        String label;
        /** Bouton survole ou non **/
        boolean survol = false;
        /** Bouton appuye ou non **/
        boolean appui = false;
    }

    /**
     * Affichage d'un bouton
     * @param bt    bouton a tracer
     */
    public static void drawBouton(Bouton bt)
    {
        // Definition de la couleur du bouton
        if(bt.survol && !bt.appui) // Si le bouton est survole mais pas appuye
            EcranGraphique.setColor(100, 0, 0);
        else if(bt.appui) // Si le bouton est appuye
            EcranGraphique.setColor(255, 0, 0);
        else // Sinon
            EcranGraphique.setColor(0, 0, 0);

        EcranGraphique.fillRect(bt.x, bt.y, bt.larg, bt.haut); // rectangle symbolisant le bouton
        EcranGraphique.setColor(255, 255, 255); // Couleur du texte
        EcranGraphique.drawString(bt.x + 4, bt.y + bt.haut - 5, EcranGraphique.COLABA8x13, bt.label); // Affichage du texte dans le bouton
    }

    /**
     * Retourne si le curseur est sur le bouton
     * @param bt   le bouton a tester
     * @return     vrai si le curseur est sur le bouton
     */
    public static boolean estSurBouton(Bouton bt)
    {
        boolean estDessus = false;

        // Si la position du curseur est dans le rectangle du bouton...
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
        // Effacage complet de l'ecran
        EcranGraphique.clear();

        // Affichage d'une grille
        EcranGraphique.setColor(0, 0, 0); // Couleur : noire
        for(int i = 0; i <= nbPiecesX; i++) // On trace les lignes verticales (1 de plus que le nb de cases en X)
        {
            EcranGraphique.drawLine(x1 + i*(imgLarg / nbPiecesX), y1, x1 + i*(imgLarg / nbPiecesX), y1 + imgHaut);
        }
        for(int i = 0; i <= nbPiecesY; i++) // On trace les lignes horizontales (1 de plus que le nb de cases en Y)
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
                if(i != pzl.caseChoisie.x || j != pzl.caseChoisie.y) /* On n'affiche pas la case tenue pour l'afficher en dernier, afin qu'elle se
                                                                                    trouve au dessus des autres */
                {
                    EcranGraphique.drawImage(pzl.pieces[i][j].pos.x,
                            pzl.pieces[i][j].pos.y, pzl.pieces[i][j].image);
                }
            }
        }

        // On a affiche presque toutes les pieces, on affiche la case tenue (si il y en a une)
        if(pzl.caseChoisie.x != -1 && pzl.caseChoisie.y != -1)
            EcranGraphique.drawImage(pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.x,
                                     pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.y,
                                     pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].image);

        
        // A la fin d'une partie, on affiche un texte
        if(estReconstitue(pzl))
        {
            EcranGraphique.drawText(x2, 50, EcranGraphique.COLABA8x13, "Tu as reussi le puzzle en "
                    + pzl.nbCoups + " coups en " + pzl.temps/1000 + " secondes");
            EcranGraphique.drawText(x2, 70, EcranGraphique.COLABA8x13, "Clique n'importe ou pour recommencer," +
                    " ou bien quitte le jeu");
        }

        // Affichage d'un menu
        drawBouton(pzl.mn.relanc);
        drawBouton(pzl.mn.taille3x4);
        drawBouton(pzl.mn.taille6x8);
        drawBouton(pzl.mn.activRotation);

        // Mise a jour de l'affichage
        EcranGraphique.flush();
    }

    /**
     * Fonction qui dit si le puzzle est fini : elle va parcourir chaque piece et verifier qu'elle est placee. Elle renvoie false a la 
	 * premiere piece mal placee.
     * @param pzl   puzzle a verifier
     * @return      vrai si le puzzle est fini
     */
    public static boolean estReconstitue(PuzzleJeu pzl)
    {
        int i = 0;
        int j;
        boolean reconstituee = true;

        while(i < nbPiecesX && reconstituee) // Tant qu'on n'a pas trouve de case non placee
        {
            j = 0; // On remet a zero j pour compter a partir de zero a chaque tour
            while(j < nbPiecesY && reconstituee) // Tant qu'on n'a pas trouve de case non placee
            {
                if(!pzl.pieces[i][j].placee || pzl.pieces[i][j].rotation != 0)
                {
                    reconstituee = false;
                }
                j++; // Incrementation de j a chaque tour de la boucle interieure
            }
            i++; // Incrementation de i a chaque tour de la premiere boucle
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
        // Initialisation des premieres variables
        pzl.temps = java.lang.System.currentTimeMillis();
        pzl.nbCoups = 0;
        
        pzl.caseChoisie = new Position2D();
        pzl.caseChoisie.x = -1;
        pzl.caseChoisie.y = -1;

        // Souris
        souris.pos = new Position2D();
        souris.pos.x = -1;
        souris.pos.y = -1;
        
        // Declarations
        pzl.pieces = new Piece[nbPiecesX][nbPiecesY]; // Tableau des pieces
        pzl.caseChoisie = new Position2D(); // Position de la case choisie
        pzl.mn = new Menu(); // Menu
        pzl.placements = new int[nbPiecesX][nbPiecesY];

        // Declaration et initialisation des boutons du Menu
        
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
        
        // Bouton activation rotation des pieces
        pzl.mn.activRotation = new Bouton();
        pzl.mn.activRotation.label = "Rotation des pieces";
        pzl.mn.activRotation.x = pzl.mn.relanc.x;
        pzl.mn.activRotation.y = pzl.mn.taille6x8.y + 35;
        pzl.mn.activRotation.larg = 10 * pzl.mn.activRotation.label.length();
        pzl.mn.activRotation.haut = 25;
        
        // Mise a zero de la grille des placements : elle est vide au depart
        for(int j = 0; j < nbPiecesY; j++)
        {
            for(int i = 0; i < nbPiecesX; i++)
            {
                pzl.placements[i][j] = 0;
            }
        }

        // Declarations
        for(int j = 0; j < nbPiecesY; j++)
        {
            for(int i = 0; i < nbPiecesX; i++)
            {
                pzl.pieces[i][j] = new Piece(); // Chaque piece
                pzl.pieces[i][j].image = new int[imgLarg / nbPiecesX][imgHaut / nbPiecesY]; // Tableau d'entiers (image)
                pzl.pieces[i][j].pos = new Position2D(); // Position de la piece
            }
        }

        // On decoupe l'image en pieces et on initialise les variables restantes
        for(int k = 0; k < nbPiecesY; k++)
        {
            for (int l = 0; l < nbPiecesX; l++) // On parcourt chaque case de la grille
            {
                for(int j = 0; j < imgHaut / nbPiecesY; j++)
                {
                    for(int i = 0; i < imgLarg / nbPiecesX; i++) // On copie chaque pixel de l'image dans la piece [l][k]
                    {
                        pzl.pieces[l][k].image[i][j] = image[l * (imgLarg / nbPiecesX) + i]
                                                            [k * (imgHaut / nbPiecesY) + j];
                        // Position de l'image : pas encore definie
                        pzl.pieces[l][k].pos.y = -1;
                        pzl.pieces[l][k].pos.x = -1;
                        pzl.pieces[l][k].placee = false; // Case au depart non-placee
                    }
                }
            }
        }
        
        // Melange des pieces
        melanger(pzl);
        
        // Rotation des pieces si la rotation est activee
        if(rotation)
            melangeRotations(pzl);
    }
    
    /**
     * Faire pivoter de 90 degres vers la droite une piece
     * @param pc   La piece a retourner de 90 degres
     */
    public static void pivoterImage(Piece pc)
    {
        // On indique la nouvelle rotation de la piece
        pc.rotation = pc.rotation + 90;
        pc.rotation = pc.rotation % 360; // Pour que la rotation soit a zero apres 270
        
        int[][] imgTrans; // image de transition
        imgTrans = new int[imgLarg / nbPiecesX][imgHaut / nbPiecesY];
        
        // On copie l'ancienne image dans notre image de transition
        for(int j = 0; j < imgHaut / nbPiecesY; j++)
        {
            for(int i = 0; i < imgLarg / nbPiecesX; i++)
            {
                imgTrans[i][j] = pc.image[i][j];
            }
        }
        
        // On copie l'image de transition apres l'avoir fait pivotee de 90 degres
        for(int j = 0; j < imgHaut / nbPiecesY; j++)
        {
            for(int i = 0; i < imgLarg / nbPiecesX; i++)
            {
                pc.image[(imgHaut / nbPiecesY) - j - 1][i] = imgTrans[i][j];
            }
        }
    }
    
    /**
     * Attribuer aleatoirement a chaque piece une rotation
     * @param pzl    Le puzzle
     */
    public static void melangeRotations(PuzzleJeu pzl)
    {
        for(int j = 0; j < nbPiecesY; j++)
        {
            for(int i = 0; i < nbPiecesX; i++)
            {
                pzl.pieces[i][j].rotation = 0;
                switch(rand(0, 3))
                {
                    case 1:
                        pivoterImage(pzl.pieces[i][j]); // 90°
                    break;
                    case 2:
                        pivoterImage(pzl.pieces[i][j]); // 90°
                        pivoterImage(pzl.pieces[i][j]); // 180°
                    break;
                    case 3:
                        pivoterImage(pzl.pieces[i][j]); // 90°
                        pivoterImage(pzl.pieces[i][j]); // 180°
                        pivoterImage(pzl.pieces[i][j]); // 270°
                    break;
                    default:
                        pzl.pieces[i][j].rotation = 0; // On ne fait rien
                }
            }
        }
    }

    /**
     * Boucle principale du jeu
     * @param pzl  puzzle
     */
    public static void jouer(PuzzleJeu pzl)
    {
        boolean relancer = false; // Si on demande a changer de puzzle dans jouerCoup

        // On joue un nouveau coup tant que le puzzle n'est pas reconstitue et qu'on n'a pas demande a changer de puzzle
        while(!estReconstitue(pzl) && !relancer)
        {
            relancer = jouerCoup(pzl);
            pzl.nbCoups++;
        }

        if(relancer) // Si on veut un autre puzzle
        {
            pzl = new PuzzleJeu(); // On en declare un nouveau
            initialiser(pzl, saisirImage()); // On l'initialise
            // melanger(pzl); // On melange les pieces --> fait dans initialiser
            jouer(pzl); // Et on rejoue :)
        }
        
        // Si l'image est reconstituee, on attend que l'utilisateur clique quelque part pour lancer une nouvelle partie
        pzl.temps = java.lang.System.currentTimeMillis() - pzl.temps;
        while(estReconstitue(pzl))
        {
            afficher(pzl); // On continue a afficher
            attendClic(); // On attend le clic
            if(souris.clicDroit || souris.clicGauche) // On attend le clic
            {
                pzl = new PuzzleJeu(); // On en declare un nouveau
                initialiser(pzl, saisirImage()); // On l'initialise
                // melanger(pzl); // On melange les pieces --> fait dans initialiser
                jouer(pzl); // On peut rejouer
            }
            EcranGraphique.wait(16);
        }
    }

    /**
     * Attend que l'utilisateur joue un coup : l'utilisateur prend une piece et la repose
     * @param pzl  Le puzzle entier
     * @return     Un booleen : vrai si il faut changer de puzzle
     */
    public static boolean jouerCoup(PuzzleJeu pzl)
    {
        boolean clk = false; // Si il y a un clic
        boolean finDuCoup = false; // Si le tour est fini
        boolean relancer = false; // Si on veut changer de puzzle
        boolean unClic; // Passe a true si il y a un clic

        int c, l; // colonne et ligne du clic de l'utilisateur

        while(!finDuCoup && !relancer)
        {
            unClic = attendClic(); // On met a jour l'etat de la souris
            if (unClic && souris.clicGauche) { // Si il y a un clic gauche
                if (!clk && estSurPiece(pzl).x != -1 && estSurPiece(pzl).y != -1) { /* Si le clic
                                                                   est sur une piece et qu'on ne tenait pas de piece */
                    pzl.caseChoisie = estSurPiece(pzl);
                    
                    c = (souris.pos.x - x1) / (imgLarg / nbPiecesX);
                    l = (souris.pos.y - y1) / (imgHaut / nbPiecesY);
                    if(c >= 0 && l >= 0 && c < nbPiecesX && l < nbPiecesY) // Si le curseur etait au dessus de la grille
                    {
                        pzl.placements[c][l] = 0; // On indique qu'il n'y a plus de piece dans la case correspondante
                    }

                    if(pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].placee) // Si la piece etait placee
                    {
                        pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].placee = false;
                    }

                    clk = true; // pour tenir la piece jusqu'au clic suivant
                }
                else if(clk) // quand on a la piece en main et qu'on clique...
                {
                    c = (souris.pos.x - x1) / (imgLarg / nbPiecesX);
                    l = (souris.pos.y - y1) / (imgHaut / nbPiecesY);
                    
                    // On verifie que le curseur est au dessus de la grille et que la case est vide
                    if(c >= 0 && l >= 0 && c < nbPiecesX && l < nbPiecesY && pzl.placements[c][l] == 0)
                    {
                        // On met la piece dans sa case (celle sous le curseur) si elle est au dessus de la grille
                        pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.x = c * (imgLarg / nbPiecesX) + x1;
                        pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.y = l * (imgHaut / nbPiecesY) + y1;
                        
                        // On dit a la grille des placements qu'il y a une piece dans cette case
                        pzl.placements[c][l] = 2;
                        
                        if(c == pzl.caseChoisie.x && l == pzl.caseChoisie.y) // Si elle est au bon endroit et que la case est vide
                        {
                            pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].placee = true;
                            // On dit a la grille des placements que la case est bien placee
                            pzl.placements[c][l] = 1;
                        }
                        finDuCoup = true; // On indique que le tour est fini
                        clk = false; // On indique que l'utilisateur a relache la piece
                        
                    }
                    else if(c >= 0 && l >= 0 && c < nbPiecesX && l < nbPiecesY && pzl.placements[c][l] != 0)
                    {
                        // finDuCoup = false; // Le tour n'est pas fini car on n'a pas lache la piece
                        clk = true; // Ici, clk = true, mais pas besoin de la repreciser, il est deja a true
                    }
                    else
                    {
                        finDuCoup = true; // On indique que le tour est fini
                        clk = false; // On indique que l'utilisateur a relache la piece
                    }
                    
                }

                // Mise a jour de l'etat des boutons (en cas de clic)
                
                if(estSurBouton(pzl.mn.relanc)) // Bouton changement de puzzle
                {
                    pzl.mn.relanc.appui = true;
                    relancer = true;
                }
                else
                    pzl.mn.relanc.appui = false;

                if(estSurBouton(pzl.mn.taille3x4)) // Bouton taille 3x4
                {
                    pzl.mn.taille3x4.appui = true;
                    nbPiecesX = 3;
                    nbPiecesY = 4;
                    relancer = true; // On change aussi de puzzle
                }
                else
                    pzl.mn.taille3x4.appui = false;

                if(estSurBouton(pzl.mn.taille6x8)) // Bouton taille 6x8
                {
                    pzl.mn.taille6x8.appui = true;
                    nbPiecesX = 6;
                    nbPiecesY = 8;
                    relancer = true; // On change aussi de puzzle
                }
                else
                    pzl.mn.taille6x8.appui = false;
                
                if(estSurBouton(pzl.mn.activRotation)) // Bouton rotation
                {
                    pzl.mn.activRotation.appui = true;
                    rotation = !rotation; // On inverse rotation
                    relancer = true; // On change aussi de puzzle
                }
                else
                    pzl.mn.taille6x8.appui = false;
            }
            else if(unClic && souris.clicDroit) // Si c'est un clic droit
            {
                if (estSurPiece(pzl).x != -1 && estSurPiece(pzl).y != -1 && rotation) // si on ne cliquait pas et qu'on est sur une piece et que la rotation est activee
                {
                    pivoterImage(pzl.pieces[estSurPiece(pzl).x][estSurPiece(pzl).y]); // On fait pivoter la piece
                    finDuCoup = true; // On indique que le tour est fini
                }
            }

            // On fait suivre le curseur par la piece
            if (clk) {
                pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.x = EcranGraphique.getMouseX() - (imgLarg / nbPiecesX)/2;
                pzl.pieces[pzl.caseChoisie.x][pzl.caseChoisie.y].pos.y = EcranGraphique.getMouseY() - (imgHaut / nbPiecesY)/2;
            }

            // Mise a jour de l'etat des boutons (survol)
            // Bouton changement de puzzle
            pzl.mn.relanc.survol = estSurBouton(pzl.mn.relanc);
            // Bouton taille 3x4
            pzl.mn.taille3x4.survol = estSurBouton(pzl.mn.taille3x4);
            // Bouton taille 6x8
            pzl.mn.taille6x8.survol = estSurBouton(pzl.mn.taille6x8);
            // Bouton rotation
            pzl.mn.activRotation.survol = estSurBouton(pzl.mn.activRotation);

            // On affiche le tout
            if(!relancer) // Pour ne pas avoir de pb dans le nb de cases a afficher lorsqu'on change le nombre de pieces
                afficher(pzl);

            EcranGraphique.wait(16); // Pour avoir environ 60 images par secondes
        }
        return relancer;
    }

    /**
     * Retourne si le curseur est sur une piece ou non
     * @param pzl    Le puzzle a tester
     * @return    boolean
     */
    public static Position2D estSurPiece(PuzzleJeu pzl)
    {
        boolean est = false; // Est sur une piece ou non
        int i;
        int j = 0;
        Position2D pos = new Position2D();

        while(!est && j < nbPiecesY)
        {
            i = 0;
            while(!est && i < nbPiecesX)
            {
                est = (souris.pos.x >= pzl.pieces[i][j].pos.x) && (souris.pos.x <= (pzl.pieces[i][j].pos.x + imgLarg / nbPiecesX))
                        && (souris.pos.y >= pzl.pieces[i][j].pos.y) && (souris.pos.y <= (pzl.pieces[i][j].pos.y
                        + (imgHaut / nbPiecesY)));
                // && !pzl.pieces[i][j].placee;
                if(est) // Est sur une piece
                {
                    pos.x = i;
                    pos.y = j;
                }
                else // N'est sur aucune piece : position a (-1,-1)
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
     * @param pzl Le puzzle a melanger
     */
    public static void melanger(PuzzleJeu pzl)
    {
        int nbCases = nbPiecesX * nbPiecesY; // Nombre total de cases
        int positions[] = new int[nbCases]; // Tableau contenant toutes les positions des cases

        // On remplit position (le tableau sera donc trie)
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
                pzl.pieces[i][j].pos.x = positions[j*(nbPiecesX)+i] % nbPiecesX;
                pzl.pieces[i][j].pos.x = x2 + (((imgLarg / nbPiecesX) + 5) * pzl.pieces[i][j].pos.x);
                pzl.pieces[i][j].pos.y = positions[j*(nbPiecesX)+i] / nbPiecesX;
                pzl.pieces[i][j].pos.y = y2 + (((imgHaut / nbPiecesY) + 5) * pzl.pieces[i][j].pos.y);
            }
        }
    }

    /**
     * Saisie d'une image : selection aleatoire de l'image parmi celles se trouvant dans /img/
     * @return  l'image (tableau d'entiers)
     */
    public static int[][] saisirImage()
    {
        int[][] img;
        img = EcranGraphique.loadPNGFile("img/" + rand(1, 31) + ".png"); // Selection de l'image entre les 31 dans /img

        // Lignes noires symbolisant les bords
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
     * Met a jour l'etat de la souris
     * @return    true si un clic est effectue
     */
    public static boolean attendClic()
    {
        boolean ret = false;

        souris.pos.x = EcranGraphique.getMouseX();
        souris.pos.y = EcranGraphique.getMouseY();

        if(EcranGraphique.getMouseState() == 0)
        {
            if(souris.clicDroit)
                souris.clicDroit = false;
            if(souris.clicGauche)
                souris.clicGauche = false;
        }
        else if(EcranGraphique.getMouseState() == 1 && EcranGraphique.getMouseButton() == 1)
        {
            if(!souris.clicGauche) // Pour ne renvoyer vrai que la premiere fois qu'on voit le clic
                ret = true;
            souris.clicGauche = true;
        }
        else if(EcranGraphique.getMouseState() == 1 && EcranGraphique.getMouseButton() == 3)
        {
            if(!souris.clicDroit) // Pour ne renvoyer vrai que la premiere fois qu'on voit le clic
                ret = true;
            souris.clicDroit = true;
        }

        return ret;
    }
}
