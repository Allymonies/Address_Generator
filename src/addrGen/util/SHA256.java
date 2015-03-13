package addrGen.util;
//By sci4me, I think.

public final class SHA256
{
  private static final int[] K = { 1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998 };
  
  public static byte[] digest(byte[] message)
  {
    byte[] hashed = new byte[32];byte[] block = new byte[64];byte[] padded = padMessage(message);
    
    int h0 = 1779033703;
    int h1 = -1150833019;
    int h2 = 1013904242;
    int h3 = -1521486534;
    int h4 = 1359893119;
    int h5 = -1694144372;
    int h6 = 528734635;
    int h7 = 1541459225;
    
    int pl64 = padded.length / 64;
    

    int[] words = new int[64];
    for (int i = 0; i < pl64; i++)
    {
      int a = h0;
      int b = h1;
      int c = h2;
      int d = h3;
      int e = h4;
      int f = h5;
      int g = h6;
      int h = h7;
      
      System.arraycopy(padded, 64 * i, block, 0, 64);
      for (int j = 0; j < 16; j++)
      {
        int j4 = j * 4;
        words[j] |= (block[j4] & 0xFF) << 24;
        words[j] |= (block[(j4 + 1)] & 0xFF) << 16;
        words[j] |= (block[(j4 + 2)] & 0xFF) << 8;
        words[j] |= block[(j4 + 3)] & 0xFF;
      }
      int j;
      for (j = 16; j < 64; j++)
      {
        int sa = words[(j - 15)];
        int sb = words[(j - 2)];
        int s0 = Integer.rotateRight(sa, 7) ^ Integer.rotateRight(sa, 18) ^ sa >>> 3;
        int s1 = Integer.rotateRight(sb, 17) ^ Integer.rotateRight(sb, 19) ^ sb >>> 10;
        words[j] = (words[(j - 16)] + s0 + words[(j - 7)] + s1);
      }
      for (j = 0; j < 64; j++)
      {
        int s0 = Integer.rotateRight(a, 2) ^ Integer.rotateRight(a, 13) ^ Integer.rotateRight(a, 22);
        int maj = a & b ^ a & c ^ b & c;
        int t2 = s0 + maj;
        int s1 = Integer.rotateRight(e, 6) ^ Integer.rotateRight(e, 11) ^ Integer.rotateRight(e, 25);
        int ch = e & f ^ (e ^ 0xFFFFFFFF) & g;
        int t1 = h + s1 + ch + K[j] + words[j];
        
        h = g;
        g = f;
        f = e;
        e = d + t1;
        d = c;
        c = b;
        b = a;
        a = t1 + t2;
      }
      h0 += a;
      h1 += b;
      h2 += c;
      h3 += d;
      h4 += e;
      h5 += f;
      h6 += g;
      h7 += h;
    }
    hashed[0] = ((byte)(h0 >>> 56 & 0xFF));
    hashed[1] = ((byte)(h0 >>> 48 & 0xFF));
    hashed[2] = ((byte)(h0 >>> 40 & 0xFF));
    hashed[3] = ((byte)(h0 >>> 32 & 0xFF));
    
    hashed[4] = ((byte)(h1 >>> 56 & 0xFF));
    hashed[5] = ((byte)(h1 >>> 48 & 0xFF));
    hashed[6] = ((byte)(h1 >>> 40 & 0xFF));
    hashed[7] = ((byte)(h1 >>> 32 & 0xFF));
    
    hashed[8] = ((byte)(h2 >>> 56 & 0xFF));
    hashed[9] = ((byte)(h2 >>> 48 & 0xFF));
    hashed[10] = ((byte)(h2 >>> 40 & 0xFF));
    hashed[11] = ((byte)(h2 >>> 32 & 0xFF));
    
    hashed[12] = ((byte)(h3 >>> 56 & 0xFF));
    hashed[13] = ((byte)(h3 >>> 48 & 0xFF));
    hashed[14] = ((byte)(h3 >>> 40 & 0xFF));
    hashed[15] = ((byte)(h3 >>> 32 & 0xFF));
    
    hashed[16] = ((byte)(h4 >>> 56 & 0xFF));
    hashed[17] = ((byte)(h4 >>> 48 & 0xFF));
    hashed[18] = ((byte)(h4 >>> 40 & 0xFF));
    hashed[19] = ((byte)(h4 >>> 32 & 0xFF));
    
    hashed[20] = ((byte)(h5 >>> 56 & 0xFF));
    hashed[21] = ((byte)(h5 >>> 48 & 0xFF));
    hashed[22] = ((byte)(h5 >>> 40 & 0xFF));
    hashed[23] = ((byte)(h5 >>> 32 & 0xFF));
    
    hashed[24] = ((byte)(h6 >>> 56 & 0xFF));
    hashed[25] = ((byte)(h6 >>> 48 & 0xFF));
    hashed[26] = ((byte)(h6 >>> 40 & 0xFF));
    hashed[27] = ((byte)(h6 >>> 32 & 0xFF));
    
    hashed[28] = ((byte)(h7 >>> 56 & 0xFF));
    hashed[29] = ((byte)(h7 >>> 48 & 0xFF));
    hashed[30] = ((byte)(h7 >>> 40 & 0xFF));
    hashed[31] = ((byte)(h7 >>> 32 & 0xFF));
    
    return hashed;
  }
  
  private static byte[] padMessage(byte[] data)
  {
    int origLength = data.length;
    int tailLength = origLength % 64;
    int padLength;
    if (64 - tailLength >= 9) {
      padLength = 64 - tailLength;
    } else {
      padLength = 128 - tailLength;
    }
    byte[] thePad = new byte[padLength];
    thePad[0] = -128;
    
    long lengthInBits = origLength * 8;
    
    int lm1 = thePad.length - 1;
    thePad[lm1] = ((byte)(int)(lengthInBits & 0xFF));
    thePad[(lm1 - 1)] = ((byte)(int)(lengthInBits >>> 8 & 0xFF));
    thePad[(lm1 - 2)] = ((byte)(int)(lengthInBits >>> 16 & 0xFF));
    thePad[(lm1 - 3)] = ((byte)(int)(lengthInBits >>> 24 & 0xFF));
    thePad[(lm1 - 4)] = ((byte)(int)(lengthInBits >>> 32 & 0xFF));
    thePad[(lm1 - 5)] = ((byte)(int)(lengthInBits >>> 40 & 0xFF));
    thePad[(lm1 - 6)] = ((byte)(int)(lengthInBits >>> 48 & 0xFF));
    thePad[(lm1 - 7)] = ((byte)(int)(lengthInBits >>> 56 & 0xFF));
    
    byte[] output = new byte[origLength + padLength];
    
    System.arraycopy(data, 0, output, 0, origLength);
    System.arraycopy(thePad, 0, output, origLength, thePad.length);
    
    return output;
  }
  
  public static long hashToLong(byte[] hash)
  {
    long ret = 0L;
    for (int i = 5; i >= 0; i--) {
      ret = Math.round(ret + (hash[i] & 0xFF) * Math.pow(256.0D, 5 - i));
    }
    return ret;
  }
}

