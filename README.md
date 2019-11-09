# The-Enigma
A sophisticated German WWII Naval Cipher with complex encryption and decryption methods implemented in java using a variety of data structures primarily: Hashmaps, ArrayLists, Scanners. This project also used extenseive string hacking and string manipulation and is able to read, scan, and encrypt config files. This project implements the generalized Enigma Machine (rotors, plugboard, reflector) This Enigma Machine was implemented to use arbitrary alphabets in encryption.

![Enigma-rotors](https://user-images.githubusercontent.com/47373165/68526380-08898d80-0290-11ea-9a06-2945f3d8302b.jpg)

The Enigma Machine uses a  progressive substitution cipher to encrypt messages making it incredibly difficult to decipher. It performed a permutation or one to one mapping on the alphabet onto itself. The Machine is comprised of a series of rotors which have 26 contacts on both sides and are wired both sides to get signals coming from either side of the rotor. How the machine works is that a signal comes through the right most rotor through one of the 26 contacts flows through the wires being permuted through each rotor before being bounced back through the reflector and inversely permuted as it traveled through the rotors again by a different route. Each rotor has a different permutation and the overall encryption of the machine depends on the order they are placed as well as there intial configuration. 
![rotors_large](https://user-images.githubusercontent.com/47373165/68526524-d547fe00-0291-11ea-8a3f-ac059ad754b1.jpg)
How the progressive substitution was implemented was as follows : Each Rotor has a ratchet to its left and a pawl to its right. Before a letter is encrypted each pawl tries to engage with the ratchet of the rotor to its right therefore rotating the rotor by one position and changing the permutation performed by the rotor. Double Stepping was also implemented as in the original Enigma where if a rotor engaged with its right rotor's ratchet, both rotors rotated. 

An example is as follows : If we have 4 rotors (1-4 ) where Rotor 1 is our reflector and Rotors 2-4 are normal moving Rotors so there are 3 pawls. Suppose each Rotor has a notch at C in its ratchet and our Alphabet contains only A-C. Now suppose we set our Rotors to position 
AAAA. The next 19 positions are listed in the image below.
![image](https://user-images.githubusercontent.com/47373165/68526589-9c5c5900-0292-11ea-9a85-fbefeb5d4b62.png)

This is the Enigma Machine and I hope you enjoyed viewing!
