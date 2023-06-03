# TheatriaSecurity

[![](https://jitpack.io/v/JL-III/GeneralUtils.svg)](https://jitpack.io/#JL-III/GeneralUtils)


TheatriaSecurity is a robust Minecraft plugin designed to provide an additional layer of security for server operators. This plugin introduces token-based authentication for players with specific permissions. It enforces this authentication for critical activities such as chatting, using commands, or moving. This is particularly useful for server operators (ops), who have extensive powers that require protection.

The underlying principle behind TheatriaSecurity is similar to two-factor authentication (2FA). It is designed to mitigate risks associated with UUID spoofing on backend servers or circumvention of other security measures.

## Features

- **Token Authentication**: Players with specific permissions are required to authenticate using tokens.
- **Activity Control**: Token authentication is enforced for chatting, command usage, and player movement.
- **Robust Security**: Provides an additional layer of security, making it more difficult for unauthorized users to exploit op powers.

## Usage

To use this plugin, a player will need access to the server files to retrieve the token. Once the player has the token, they can authenticate and use their permissions as normal. Tokens are designed to be one-time use, ensuring that even if a token is compromised, it cannot be used more than once.
