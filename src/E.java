/*
Classe E.java
Cree par Smiley32 pour simplifier les entrees et sorties (particulierement les entrees au clavier).
Ne pas redistribuer sans autorisations.
Utilisation :
E.a(...) --> afficher qqc
E.ln(...) --> afficher qqc avec saut de ligne (ou bien juste saut de ligne si aucun argument)
E.get_String(*) --> saisie d'une chaîne de caractères
E.get_int(*) --> saisie d'un entier
E.get_double(*) --> saisie d'un double
E.get_char(*) --> saisie d'un char
* --> arg optionnel pour afficher une question avant la saisie
*/
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

class E
{
    public static void main(String args[])
    {
        System.out.println("Classe E cree par Smiley32 pour les entrees et sorties.");
    }

    public static String get_String()
    {
        String ligne_lue = null;
        try
        {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            ligne_lue = br.readLine();
        }
        catch(IOException e)
        {
            System.err.println(e);
        }
        return ligne_lue;
    }

    public static String get_String(Object... s)
    {
        for (Object o : s)
        {
            ln(o.toString());
        }
        return get_String();
    }

  /*public static int get_int()
  {
    return Integer.parseInt(get_String());
  }*/

    public static int get_int()
    {
        int entier = 0;
        boolean continuer = true;
        while (continuer)
        {
            try
            {
                entier = Integer.parseInt(get_String());
                continuer = false;
            }
            catch(Exception e)
            {
                ln("La saisie n'est pas du type souhaité. Recommencez.");
            }
        }
        return entier;
    }

    public static int get_int(Object... s)
    {
        for (Object o : s)
        {
            ln(o.toString());
        }
        return get_int();
    }

    public static double get_double()
    {
        double reel = 0;
        boolean continuer = true;
        while (continuer)
        {
            try
            {
                reel = Double.parseDouble(get_String());
                continuer = false;
            }
            catch (Exception e)
            {
                ln("La saisie n'est pas du type souhaité. Recommencez.");
            }
        }
        return reel;
    }

    public static double get_double(Object... s)
    {
        for (Object o : s)
        {
            ln(o.toString());
        }
        return get_double();
    }

    public static char get_char()
    {
        return get_String().charAt(0);
    }

    public static char get_char(Object... s)
    {
        for (Object o : s)
        {
            ln(o.toString());
        }
        return get_char();
    }

    public static void a(Object... s)
    {
        for (Object o : s)
        {
            System.out.print(o.toString());
        }
    }

    public static void a()
    {
        System.out.print("");
    }

    public static void ln(Object... s)
    {
        for (Object o : s)
        {
            System.out.println(o.toString());
        }
    }

    public static void ln()
    {
        System.out.println();
    }
}
