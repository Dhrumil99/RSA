import java.io.*;
import java.util.*;
import java.lang.Math;
public class Main
{
    public long random_no, random_e, p, q, e, d;
    public long N, phi_N;
    public static String str_msg, block, s_block, s_str_msg;
    public static String [] blocks = new String [100];
    public static String [] s_blocks = new String [100];
    public static long ciphertext[] = new long[100];
    public static long s_ciphertext[] = new long[100];
    public static int cipher_len, s_cipher_len;
    
    public boolean isPrime(long num)            //To check if number is prime
    {
        boolean flag = false;
        for (int i = 2; i <= num / 2; ++i) 
        {
            // condition for nonprime number
            if (num % i == 0)
            {
                flag = true;
                break;
            }
        }
        if (!flag)
        {
          //System.out.println(num + " is a prime number.");
          long random_no = num;
        }
        else
        {
          //System.out.println(num + " is not a prime number.");
        }
        return flag;
    }
    
    public long random_generator()              //To generate random P & Q values
    {
        long min = 32768;
        long max = 65535;
        long range = max - min + 1;
        random_no = (long)(Math.random() * range) + min;
        return random_no;
    }
    
    public long random_generator_e(long n_phi)  //To generate random E values
    {
        long min = 1;
        long max = n_phi;
        long range = max - min + 1;
        random_e = (long)(Math.random() * range) + min;
        return random_e;
    }
    
    public static long gcd(long e, long z)             //To check GCD of E and Phi(N)
    {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }
    
    public void message_work()                      // To break message into blocks of 3 chars
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter plaintext message : ");
        str_msg = sc.nextLine();
        int len = str_msg.length();
        int count = 0;
        
        for(int i = 0 ; i < len-2 ; i=i+3)
        {
            block="";
            block = block + str_msg.charAt(i) + str_msg.charAt(i+1) + str_msg.charAt(i+2);
            //System.out.println(block);
            blocks[count] = block;
            ++count;
        }
        int i=0,ind=0;
        while(blocks[i] != null)
        {
            //System.out.println(blocks[i]);
            ++i;
            ind = i;
        }
        int last = len%3;
        if (last != 0)
        {    
            String s_last = str_msg == null || str_msg.length() < last ? str_msg : str_msg.substring(str_msg.length() - last);
            blocks[ind] = s_last;
            i=0;
            while(blocks[i] != null)
            {
                //System.out.print(blocks[i] + ",");
                ++i;
            }
        }
    }
    
    public int toHex_toInt(String str)      // From String to Dec
    {
        Scanner sc = new Scanner(System.in);
        //System.out.println("Enter a String value: ");
        //String str = sc.next();
        StringBuffer sb = new StringBuffer();
        //Converting string to character array
        char ch[] = str.toCharArray();
        for(int i = 0; i < ch.length; i++) 
        {
            String hexString = Integer.toHexString(ch[i]);
            sb.append(hexString);
        }
        String result = sb.toString();
        int num = Integer.parseInt(result,16);
        //System.out.println(result);
        //System.out.println(num);
        return num;
    }
    
    public long encrypt(long x, long y, long p)
    {
        long res = 1; // Initialize result
        x = x % p;
        if (x == 0)
            return 0;
        while (y > 0)
        {
            if ((y & 1) != 0)
                res = (res * x) % p;
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }
    
    static long modInverse(long a, long m)      // To Find Inverse Mod to calculate D value
    {
        for (long x = 1; x < m; x++)
            if (((a%m) * (x%m)) % m == 1)
                return (long)x;
        return 1;
    }
    
    public static void cipher_input()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Cipher Length : ");
        cipher_len = sc.nextInt();
        System.out.println("Enter Ciphertext : ");
        for(int i=0;i<cipher_len;i++)
        {
            ciphertext[i] = sc.nextLong();
        }
    }
    
    public static String hexToASCII(long hexValue)
    {
        String s = (Long.toHexString(hexValue));
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < s.length(); i += 2)
        {
            String str = s.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }
    
    public void signature_work()                      // To break signature into blocks of 3 chars
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter plaintext Signature (Sender's Name) : ");
        s_str_msg = sc.nextLine();
        int len = s_str_msg.length();
        int count = 0;
        
        for(int i = 0 ; i < len-2 ; i=i+3)
        {
            s_block="";
            s_block = s_block + s_str_msg.charAt(i) + s_str_msg.charAt(i+1) + s_str_msg.charAt(i+2);
            //System.out.println(block);
            s_blocks[count] = s_block;
            ++count;
        }
        int i=0,ind=0;
        while(s_blocks[i] != null)
        {
            //System.out.println(blocks[i]);
            ++i;
            ind = i;
        }
        int last = len%3;
        if (last != 0)
        {    
            String s_last = s_str_msg == null || s_str_msg.length() < last ? s_str_msg : s_str_msg.substring(s_str_msg.length() - last);
            s_blocks[ind] = s_last;
            i=0;
            while(s_blocks[i] != null)
            {
                //System.out.print(s_blocks[i] + ",");
                ++i;
            }
        }
    }
    
    public static void s_cipher_input()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter Cipher Signature Length : ");
        s_cipher_len = sc.nextInt();
        System.out.println("Enter Ciphered Signature (Partner's Value)");
        for(int i=0;i<s_cipher_len;i++)
        {
            s_ciphertext[i] = sc.nextLong();
        }
        System.out.println();
    }
    
    public static void main(String [] args)             //Main Function
    {
        Main obj = new Main();

        // P and Q Generation ------------------------------------------------------------------------------------------ !!
        
        while(obj.isPrime(obj.random_generator()) == true)
        {
            continue;
        }
        //obj.p = obj.random_no;
        obj.p = 46171;
        System.out.println("p = " + obj.p);
        
        while(obj.isPrime(obj.random_generator()) == true)
        {
            continue;
        }
        //obj.q = obj.random_no;
        obj.q = 44537;
        System.out.println("q = " + obj.q);
        
        //-------------------------------------------------------------------------------------------------------------- !!
        
        // N and Phi(N) Calculation ------------------------------------------------------------------------------------ !!
        
        //obj.N = (long)(obj.p * obj.q);
        obj.N = (long)2056317827;      //Dhrumil's N value
        //obj.N = (long)2814941131L;      //Partner's N value
        //obj.phi_N = (long)((obj.p - 1) * (obj.q - 1));
        obj.phi_N = (long)2056227120;      //Dhrumil's Phi(N) value
        
        System.out.println("N = " + obj.N);
        System.out.println("Phi(N) = " + obj.phi_N);
        
        //-------------------------------------------------------------------------------------------------------------- !!
        
        // E Generation ------------------------------------------------------------------------------------------------ !!
        // Message to blocks conversion -------------------------------------------------------------------------------- !!
        
        long E = obj.random_generator_e(obj.phi_N);
        while(gcd(E, obj.phi_N) != 1)
        {
            E = obj.random_generator_e(obj.phi_N);
            continue;
        }
        //obj.e = obj.random_e;
        
        //obj.e = (long)1283918749;       //Partner's E value
        obj.e = (long)1616418191;       //Dhrumil's E value
        System.out.println("E = " + obj.e);
        
        //-------------------------------------------------------------------------------------------------------------- !!
        
        System.out.println("\n\nMy public key in (N,e) format = (" + obj.N + ", " + obj.e + ")\n");
        
        System.out.println("\n-------------------------------------------------------------------------------------------------------");
        System.out.println("\nENCRYPTION : \n");
        // Message to blocks conversion -------------------------------------------------------------------------------- !!
        
        obj.message_work();             // To split message into blocks of 3
        
        //-------------------------------------------------------------------------------------------------------------- !!
        
        //System.out.println(obj.toHex_toInt(blocks[0]));  // Str -> Hex -> Dec
        System.out.println("\nCipher Text is : (computed using partner's public key)");
        int i=0;
        while(blocks[i] != null)
        {
            //System.out.println(obj.toHex_toInt(blocks[i]));
            System.out.print(obj.encrypt(obj.toHex_toInt(blocks[i]),1283918749,2814941131L) + " ");
            ++i;
        }
        i=0;
        System.out.println("\n\n-------------------------------------------------------------------------------------------------------");
        System.out.println("\nDECRYPTION : \n");
        
        // Decryption -------------------------------------------------------------------------------------------------- !!
        
        
        cipher_input();
        //obj.d = modInverse(obj.e, obj.phi_N)
        obj.d = (long)1438492991;        //Dhrumil's D value
        //obj.d = (long)493415149;        //Partner's D value
        System.out.println("\n\nMy private key in (N,d) format = (" + obj.N + ", " + obj.d + ")\n");
        
        System.out.println("\nThe Decrypted Message is : (computed using personal private key)");
        for(i=0;i<cipher_len;i++)
        {
            System.out.print(hexToASCII(obj.encrypt(ciphertext[i], obj.d, obj.N)));
        }
        System.out.println("\n\n-------------------------------------------------------------------------------------------------------");
        //-------------------------------------------------------------------------------------------------------------- !!
        
        // Signature --------------------------------------------------------------------------------------------------- !!
        
        obj.signature_work();
        System.out.println("\nCiphered Signature is : (computed using personal private key)");
        i=0;
        while(s_blocks[i] != null)
        {
            //System.out.println(obj.toHex_toInt(blocks[i]));
            System.out.print(obj.encrypt(obj.toHex_toInt(s_blocks[i]),obj.d,obj.N) + " ");
            ++i;
        }
        
        System.out.println("\n\n-------------------------------------------------------------------------------------------------------");
        
        
        s_cipher_input();
        System.out.println("\nThe Decrypted Signature is : (computed using partner's public key)");
        for(i=0;i<s_cipher_len;i++)
        {
            System.out.print(hexToASCII(obj.encrypt(s_ciphertext[i], 1283918749, 2814941131L)));
        }
        
        System.out.println("\n\n-------------------------------------------------------------------------------------------------------");
        
        //-------------------------------------------------------------------------------------------------------------- !!
    }
}