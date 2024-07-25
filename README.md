# Encrypter

This is a small library used to encrypt passwords.



`com.lobosstudios.crypter.Encrypter.encrypt(String unencrypted, String ourSalt)` generates a hash using SHA-512. The encrypted password is prefixed with the string `{CRYPT}` (for historical reasons that probably won't make sense to anyone except me; I lifted the code from a small library I wrote years ago).

If you are generating a hash from a plaintext password, `ourSalt` must be null. The salt is only specified when you are verifying a password.

`com.lobosstudios.crypter.Encrypter. verify(String encrypted, String passwordToVerify)` returns true if the encrypted hash matches the unencrypted password. The passwords are compared this way:
* The salt is extract from the encrypted password.
* `encrypt()` is called, passing the plaintext password and the salt. 
* Given the same salt and the same plaintext password, `encrypt()` will return the same string we passed in. If the string we generate is identical to `encrypted`, we return true; otherwise we return false.

## CLI

`java -jar crypter-VERSION-all.jar [ -v | --verify]`

Allows you to generate a password, and copies the encrypted password to the clipboard, unless `-v` or `--verify` is specified, in which case you are prompted 
for the encrypted password, then you are prompted for a plaintext password, and the two are compared; you are told either that they match, or they don't match.

If `-v` or `--verify` is specified, the option must come AFTER `-jar`.

## Dependencies

Crypter's only dependency is on the Apache Commons Codec library.

## Maven Coordinates

Crypter is hosted in Lobos Studios' `java-dev` repository.

To use the repo with Gradle:

```groovy
repositories {
    mavenCentral()
    maven {
        url "https://repos.lobosstudios.com/repository/java-dev/"
    }
}

dependencies {
    implementation "com.lobosstudios:crypter:[VERSION]"
}
```

To use it with Maven:

```xml
    <repositories>
        <repository>
            <id>lobosstudios-nexus-java-dev</id>
            <name>Lobos Studios java-dev</name>
            <url>https://repos.lobosstudios.com/repository/java-dev/</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.lobosstudios</groupId>
            <artifactId>crypter</artifactId>
            <version>[VERSION]</version>
        </dependency>
    </dependencies>
```

***repos.lobosstudios.com is hosted on a Sonatype Nexus server. Repos hosted on Nexus require the slash at the end of the URL. Don't forget it!***
