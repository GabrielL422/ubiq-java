package com.ubiqsecurity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;



import java.util.Arrays;
import ubiqsecurity.fpe.FF1;
import ubiqsecurity.fpe.FF3_1;


import org.bouncycastle.crypto.InvalidCipherTextException;

public class UbiqFPEEncrypt implements AutoCloseable {
    private int usesRequested;

    private UbiqWebServices ubiqWebServices; // null when closed
    private int useCount;
    private EncryptionKeyResponse encryptionKey;
    private AesGcmBlockCipher aesGcmBlockCipher;

    public UbiqFPEEncrypt(UbiqCredentials ubiqCredentials, int usesRequested) {
        this.usesRequested = usesRequested;
        this.ubiqWebServices = new UbiqWebServices(ubiqCredentials);
    }

    public void close() {
        if (this.ubiqWebServices != null) {
            if (this.encryptionKey != null) {
                // if key was used less times than requested, notify the server.
                if (this.useCount < this.usesRequested) {
                    System.out.println(String.format("UbiqFPEEncrypt.close: reporting key usage: %d of %d", this.useCount,
                            this.usesRequested));
                    this.ubiqWebServices.updateEncryptionKeyUsage(this.useCount, this.usesRequested,
                            this.encryptionKey.KeyFingerprint, this.encryptionKey.EncryptionSession);
                }
            }

            this.ubiqWebServices = null;
        }
    }




    public static String encryptFF1(UbiqCredentials ubiqCredentials, byte[] tweek, int radix, String PlainText)
            throws IllegalStateException, InvalidCipherTextException {
            
            
        // STUB - For now, hardcode a key    
        final byte[] key = {
            (byte)0x2b, (byte)0x7e, (byte)0x15, (byte)0x16,
            (byte)0x28, (byte)0xae, (byte)0xd2, (byte)0xa6,
            (byte)0xab, (byte)0xf7, (byte)0x15, (byte)0x88,
            (byte)0x09, (byte)0xcf, (byte)0x4f, (byte)0x3c,
            (byte)0xef, (byte)0x43, (byte)0x59, (byte)0xd8,
            (byte)0xd5, (byte)0x80, (byte)0xaa, (byte)0x4f,
            (byte)0x7f, (byte)0x03, (byte)0x6d, (byte)0x6f,
            (byte)0x04, (byte)0xfc, (byte)0x6a, (byte)0x94,
        };
        
        // STUB - tweek ranges
        final long twkmin= 0;
        final long twkmax= 0;
        
            

        try (UbiqEncrypt ubiqEncrypt = new UbiqEncrypt(ubiqCredentials, 1)) {
            FF1 ctx;
            ctx = new FF1(Arrays.copyOf(key, 16), tweek, twkmin, twkmax, radix); 
            String cipher = ctx.encrypt(PlainText);
        
            System.out.println("encryptFF1 PlainText= " + PlainText);
            System.out.println("encryptFF1 cipher= " + cipher);
        
        
            return cipher;
        }
    }
    
    
    

    public static String encryptFF3_1(UbiqCredentials ubiqCredentials, byte[] tweek, int radix, String PlainText)
            throws IllegalStateException, InvalidCipherTextException {
            
            
        // STUB - For now, hardcode a key    
        final byte[] key = {
            (byte)0xef, (byte)0x43, (byte)0x59, (byte)0xd8,
            (byte)0xd5, (byte)0x80, (byte)0xaa, (byte)0x4f,
            (byte)0x7f, (byte)0x03, (byte)0x6d, (byte)0x6f,
            (byte)0x04, (byte)0xfc, (byte)0x6a, (byte)0x94,
            (byte)0x3b, (byte)0x80, (byte)0x6a, (byte)0xeb,
            (byte)0x63, (byte)0x08, (byte)0x27, (byte)0x1f,
            (byte)0x65, (byte)0xcf, (byte)0x33, (byte)0xc7,
            (byte)0x39, (byte)0x1b, (byte)0x27, (byte)0xf7,
        };
        

        try (UbiqEncrypt ubiqEncrypt = new UbiqEncrypt(ubiqCredentials, 1)) {
            FF3_1 ctx;
            ctx = new FF3_1(Arrays.copyOf(key, 16), tweek, radix); 
            String cipher = ctx.encrypt(PlainText);
        
            System.out.println("encryptFF3_1 PlainText= " + PlainText);
            System.out.println("encryptFF3_1 cipher= " + cipher);
        
        
            return cipher;
        }
    }
    
    
    
    
    
        
    
//     public static String encryptFF1(String PlainText) {
//         final byte[] key = {
//             (byte)0x2b, (byte)0x7e, (byte)0x15, (byte)0x16,
//             (byte)0x28, (byte)0xae, (byte)0xd2, (byte)0xa6,
//             (byte)0xab, (byte)0xf7, (byte)0x15, (byte)0x88,
//             (byte)0x09, (byte)0xcf, (byte)0x4f, (byte)0x3c,
//             (byte)0xef, (byte)0x43, (byte)0x59, (byte)0xd8,
//             (byte)0xd5, (byte)0x80, (byte)0xaa, (byte)0x4f,
//             (byte)0x7f, (byte)0x03, (byte)0x6d, (byte)0x6f,
//             (byte)0x04, (byte)0xfc, (byte)0x6a, (byte)0x94,
//         };
//         final byte[] twk = {
//             (byte)0x39, (byte)0x38, (byte)0x37, (byte)0x36,
//             (byte)0x35, (byte)0x34, (byte)0x33, (byte)0x32,
//             (byte)0x31, (byte)0x30,
//         };
//         final long twkmin= 0;
//         final long twkmax= 0;
//         final int radix= 10;
//         
//         FF1 ctx;
//         ctx = new FF1(Arrays.copyOf(key, 16), twk, twkmin, twkmax, radix); 
//         String cipher = ctx.encrypt(PlainText);
//         
//         
//         
//         System.out.println("encryptFF1 PlainText= " + PlainText);
//         System.out.println("encryptFF1 cipher= " + cipher);
//         
//         
//         return cipher;
//     }
//     
//     
//     
// 
// 
// 
//     public static String encryptFF3_1(String PlainText) {
//         final byte[] key = {
//             (byte)0xef, (byte)0x43, (byte)0x59, (byte)0xd8,
//             (byte)0xd5, (byte)0x80, (byte)0xaa, (byte)0x4f,
//             (byte)0x7f, (byte)0x03, (byte)0x6d, (byte)0x6f,
//             (byte)0x04, (byte)0xfc, (byte)0x6a, (byte)0x94,
//             (byte)0x3b, (byte)0x80, (byte)0x6a, (byte)0xeb,
//             (byte)0x63, (byte)0x08, (byte)0x27, (byte)0x1f,
//             (byte)0x65, (byte)0xcf, (byte)0x33, (byte)0xc7,
//             (byte)0x39, (byte)0x1b, (byte)0x27, (byte)0xf7,
//         };
//         final byte[] twk = {
//             (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
//             (byte)0x00, (byte)0x00, (byte)0x00,
//         };
//         final int radix= 10;
//         
//         FF3_1 ctx;
//         ctx = new FF3_1(Arrays.copyOf(key, 16), twk, radix); 
//         String cipher = ctx.encrypt(PlainText);
//         
//         
//         
//         System.out.println("encryptFF3_1 PlainText= " + PlainText);
//         System.out.println("encryptFF3_1 cipher= " + cipher);
//         
//         
//         return cipher;
//     }
    
    
    
        
    
    
    
}
