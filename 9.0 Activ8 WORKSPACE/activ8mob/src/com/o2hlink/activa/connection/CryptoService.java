package com.o2hlink.activa.connection;

/**
 *      <hr/><b> E-BusinessJavaTemplate <br>
 *      Copyright 2005-2006 Motorola, Inc. All Rights Reserved.<br>
 *      MOTOROLA CONFIDENTIAL PROPRIETARY WHEN FILLED OUT COMPLETELY<br>
 *      Template No. EBF0018 Template Version No. 01.03.00</B> <hr/>
 *
 *  Provides AES and MD5 crypto services using BouncyCastle lightweight API. <P>
 *
 *  Filename:    /Source/MIDlet/src/com/motorola/motohealth/protocol/CryptoService.java<P>
 * 
 *  Design Tracking: <P>
 *  Spec Path:   Refer to MOTOHEALTH CI Location document<P>
 *  Design Tag:  Refer to MOTOHEALTH Traceability Matrix<P>
 *
 *  Revision Information:<p>
 *  Date:           Author:                  Tracking #:           Description:<p>
 *  --------------- ------------------------ --------------------- ------------------------------------<p>
 * 	05Jan2005		Pablo Bertetti           ISGcq00278182		   File creation
 *	11Jan2005		Sebastian Ganame		 ISGcq00278182		   Changed package name due to file relocation
 *  01Mar2005       Pablo Bertetti           ISGcq00278490         Updated copyright line
 * 	10Jan2006		Sebastian Ganame		 ISGcq00369212         Changed directory for R02.00.00
 *	11Jan2006		Sebastian Ganame		 ISGcq00369212         Fixed in-file copyright, traceability and versioning 
 */

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

public class CryptoService
{

      /**
       * This function encrypts a given byte array with a specified key using
       * the AES/ECB/PKCS5 algorithm.
       * 
       * @param - data The byte array to encrypt
       * @param - key The byte array containing the key. It shall be of at least
       *        128 bit long.
       * @return A byte array containing the encrypted data
       * @throws Exception
       */
      public static byte[] encryptAES(byte[] data, byte[] key) throws Exception
      {

            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(
                        new AESEngine());
            cipher.init(true, new KeyParameter(key));
            return callCipher(cipher, data);
      }

      /**
       * This function decrypts a previously encrypted byte array with a
       * specified key using the AES/ECB/PKCS5 algorithm.
       * 
       * @param - data The byte array to decrypt
       * @param - key The byte array containing the key. It shall be of at least
       *        128 bit long.
       * @return A byte array containing the decrypted data
       * @throws Exception
       */
      public static byte[] decryptAES(byte[] data, byte[] key) throws Exception
      {

            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(
                        new AESEngine());
            cipher.init(false, new KeyParameter(key));
            return callCipher(cipher, data);
      }

      /**
       * This function computes an MD5 digest over a given byte array
       * 
       * @param data - The byte array over which the digest will be calculated
       * @return A byte array containing the MD5 digest
       */
      public static byte[] computeMD5(byte[] data)
      {

            Digest digestEngine = new MD5Digest();
            int digestSize = digestEngine.getDigestSize();
            byte[] digest = new byte[digestSize];
            digestEngine.update(data, 0, data.length);
            digestEngine.doFinal(digest, 0);
            return digest;
      }

      /**
       * Calls the BouncyCastle's main encryption/decryption routine
       */
      private static byte[] callCipher(BufferedBlockCipher cipher, byte[] data)
                  throws CryptoException
      {

            int size = cipher.getOutputSize(data.length);
            byte[] result = new byte[size];
            int olen = cipher.processBytes(data, 0, data.length, result, 0);
            olen += cipher.doFinal(result, olen);
            if (olen < size)
            {
                  byte[] tmp = new byte[olen];
                  System.arraycopy(result, 0, tmp, 0, olen);
                  result = tmp;
            }
            return result;
      }

}