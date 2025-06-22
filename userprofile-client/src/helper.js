import { pemPrivateKey } from './key';

function base64ToArrayBuffer(base64) {
    try {
        // Clean and normalize the base64 string
        base64 = base64.replace(/\s/g, '');

        // Add padding if needed
        while (base64.length % 4 !== 0) {
            base64 += '=';
        }

        const binaryString = atob(base64);
        const bytes = new Uint8Array(binaryString.length);
        for (let i = 0; i < binaryString.length; i++) {
            bytes[i] = binaryString.charCodeAt(i);
        }
        return bytes.buffer;
    } catch (error) {
        console.error('Base64 conversion error:', error);
        throw new Error('Invalid Base64 data');
    }
}

export const decryptData = async (encryptedBase64) => {
    try {
        console.log('Starting decryption process with RSA/ECB/OAEPWithSHA-256AndMGF1Padding');

        // Clean the key - remove headers, footers, and whitespace
        const pemContents = pemPrivateKey
            .replace(/-----BEGIN.*?-----/, '')
            .replace(/-----END.*?-----/, '')
            .replace(/\s+/g, '');

        // Convert key from Base64 to binary
        const binaryKey = base64ToArrayBuffer(pemContents);
        console.log('Key binary length:', binaryKey.byteLength);

        // Convert encrypted data from Base64 to binary
        const encryptedBinary = base64ToArrayBuffer(encryptedBase64);
        console.log('Encrypted data length:', encryptedBinary.byteLength);

        // Import the private key with SHA-256 (to match the server's OAEPWithSHA-256AndMGF1Padding)
        const privateKey = await window.crypto.subtle.importKey(
            'pkcs8',                    
            binaryKey,
            {
                name: 'RSA-OAEP',      
                hash: 'SHA-256'        
            },
            false,                     
            ['decrypt']                
        );

        console.log('Private key imported successfully');

        // Decrypt the data with parameters matching the server
        const decryptedBuffer = await window.crypto.subtle.decrypt(
            {
                name: 'RSA-OAEP'        
            },
            privateKey,
            encryptedBinary
        );

        // Convert ArrayBuffer to string
        const decoder = new TextDecoder('utf-8');
        const decrypted = decoder.decode(decryptedBuffer);
        console.log('Decryption successful');

        return decrypted;
    } catch (error) {
        console.error('Decryption error:', error);

        // Check for specific issues
        if (error.name === 'OperationError') {
            console.error('This could indicate a mismatch between encryption and decryption parameters');
            console.error('Verify that the private key matches the public key used for encryption');
        }

        throw new Error(`Decryption failed: ${error.message}`);
    }
}