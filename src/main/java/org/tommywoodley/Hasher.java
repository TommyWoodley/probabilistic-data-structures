package org.tommywoodley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
  static final String hashName = "MD5"; // MD5 gives good enough accuracy in most circumstances. Change to SHA1 if it's needed
  static final MessageDigest digestFunction;
  static { // The digest method is reused between instances
    MessageDigest tmp;
    try {
      tmp = java.security.MessageDigest.getInstance(hashName);
    } catch (NoSuchAlgorithmException e) {
      tmp = null;
    }
    digestFunction = tmp;
  }

  public static int[] createHashes(byte[] data, int hashes) {
    int[] result = new int[hashes];

    int k = 0;
    byte salt = 0;
    while (k < hashes) {
      byte[] digest;
      synchronized (digestFunction) {
        digestFunction.update(salt);
        salt++;
        digest = digestFunction.digest(data);
      }

      for (int i = 0; i < digest.length / 4 && k < hashes; i++) {
        int h = 0;
        for (int j = (i*4); j < (i*4)+4; j++) {
          h <<= 8;
          h |= ((int) digest[j]) & 0xFF;
        }
        result[k] = h;
        k++;
      }
    }
    return result;
  }

}
