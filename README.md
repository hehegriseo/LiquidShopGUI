# LiquidShopGUI

A modern economy and GUI shop plugin for **Paper 1.21+** built with **Java 21** and **Gradle Kotlin DSL**.

> **⚠️ UNDER DEVELOPMENT** — This project is in early active development. Features, APIs, and configuration are subject to change. Not yet recommended for production servers.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Building from Source](#building-from-source)
- [Commands](#commands)
- [Permissions](#permissions)
- [Configuration](#configuration)
- [Planned Features](#planned-features)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

LiquidShopGUI is a lightweight, all-in-one economy and shop plugin designed for Paper 1.21+ servers. It provides an in-memory economy system with player-to-player payments, item trading (worth/sell system), admin management tools, and a GUI-based shop — all in a single JAR with zero external dependencies.

The plugin uses the Adventure API for rich, color-coded chat messages and follows modern Paper API practices with no deprecated methods.

---

## Features

### Economy System
- **In-memory balance storage** — fast, no database setup required
- **First-join starting balance** — configurable amount granted automatically
- **Player-to-player payments** — `/pay` with validation and confirmation messages
- **Balance leaderboard** — `/balancetop` displays the top 10 richest players with ranked colors

### Item Trading
- **Item valuation** — `/worth` checks the configured monetary value of any item
- **Bulk selling** — `/sell hand|all|blocks` sells items instantly for cash
- **Admin price control** — `/setworth` sets item prices that persist in config.yml

### Admin Tools
- **Full economy control** — give, take, set, and reset balances
- **Hot reload** — reload configuration without restarting the server
- **Permission-based** — all admin commands protected by `liquidshopgui.admin`

### Shop GUI *(coming soon)*
- 54-slot inventory with categorized items
- Buy and sell directly from the GUI
- Configurable shop layout via shops.yml

---

## Requirements

| Dependency | Version |
|------------|---------|
| Java | 21 or higher |
| Server | Paper 1.21+ |
| Build Tool | Gradle 8.12+ (wrapper included) |

---

## Installation

1. Download the latest `LiquidShopGUI.jar` from the [Releases](https://github.com/hehegriseo/LiquidShopGUI/releases) page (or [build from source](#building-from-source)).
2. Place the JAR file into your server's `plugins/` folder.
3. Restart your server (or use a plugin manager like PlugMan to load it).
4. Edit `plugins/LiquidShopGUI/config.yml` to customize settings.
5. Run `/eco reload` in-game to apply changes.

On first run, the plugin creates the following files:

```
plugins/LiquidShopGUI/
├── config.yml          # Main configuration
├── messages.yml        # Message templates (future use)
└── shops.yml           # Shop category definitions (future use)
```

---

## Building from Source

### Prerequisites
- Java 21 JDK installed
- Git installed

### Steps

```bash
git clone https://github.com/hehegriseo/LiquidShopGUI.git
cd LiquidShopGUI
./gradlew build
```

The compiled JAR will be at `build/libs/LiquidShopGUI.jar`.

### Build Output

```
BUILD SUCCESSFUL in 2s
4 actionable tasks: 4 executed
```

---

## Commands

### Player Commands

| Command | Aliases | Description |
|---------|---------|-------------|
| `/balance` | `/bal` | Check your current account balance |
| `/balancetop` | `/baltop` | Display the top 10 richest players on the server |
| `/pay <player> <amount>` | — | Send money to another online player |
| `/worth [item]` | — | Check the value of an item (uses held item if no argument given) |
| `/sell [hand\|all\|blocks]` | — | Sell items for cash. `hand` = held item, `all` = everything with value, `blocks` = block materials only |
| `/shop` | — | Open the GUI shop |

### Admin Commands

All admin commands require the `liquidshopgui.admin` permission (default: operators).

| Command | Description |
|---------|-------------|
| `/eco give <player> <amount>` | Add money to a player's balance |
| `/eco take <player> <amount>` | Remove money from a player's balance |
| `/eco set <player> <amount>` | Set a player's exact balance |
| `/eco reset <player>` | Reset a player's balance to zero |
| `/eco reload` | Reload the configuration file without restarting |
| `/setworth <item> <price>` | Set or remove (price = 0) a server-defined item value |

---

## Permissions

| Permission | Default | Description |
|------------|---------|-------------|
| `liquidshopgui.admin` | `op` | Access to all admin commands (`/eco`, `/setworth`) |

Player commands (`/balance`, `/pay`, `/balancetop`, `/worth`, `/sell`, `/shop`) are available to all players by default and do not require any permission node.

---

## Configuration

### config.yml

```yaml
currency-symbol: "$"
starting-balance: 1000
shop-title: "Liquid Shop"

worth:
  DIAMOND: 1000.0
  IRON_INGOT: 50.0
  GOLD_INGOT: 100.0
  EMERALD: 200.0
  NETHERITE_INGOT: 5000.0
  OAK_LOG: 5.0
  COBBLESTONE: 2.0
  WHEAT: 3.0
```

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `currency-symbol` | String | `$` | Symbol displayed before all monetary values |
| `starting-balance` | Number | `1000` | Amount granted to players on their first join |
| `shop-title` | String | `Liquid Shop` | Title of the shop GUI inventory |
| `worth.<MATERIAL>` | Number | (none) | Per-item monetary value used by `/worth` and `/sell` |

Item worth values can be managed in-game using `/setworth <item> <price>` — changes are saved to `config.yml` automatically. Set a price to `0` to remove the item's worth entry.

### messages.yml

```yaml
prefix: "<gold>[Liquid]</gold>"
```

Reserved for future use with configurable message templates.

### shops.yml

```yaml
categories: {}
```

Reserved for future use — will define shop categories, items, prices, and slot positions for the GUI shop.

---

## Planned Features

- [ ] **Shop GUI** — 54-slot categorized inventory with buy/sell actions
- [ ] **SQLite Persistence** — player balances survive server restarts
- [ ] **Scoreboard** — configurable sidebar showing balance, playtime, and rank
- [ ] **Tab Completion** — command argument suggestions for player names, items, and subcommands
- [ ] **Vault Support** — compatibility layer for other economy-reliant plugins
- [ ] **Messages Config** — full message customization via messages.yml
- [ ] **Multi-language Support** — locale-based message files
- [ ] **Bank Notes** — item-based value transfer
- [ ] **Interest System** — automatic balance growth over time

---

## Project Structure

```
src/main/java/com/vishal/liquidshopgui/
├── LiquidShopGUI.java          # Main plugin class
├── commands/
│   ├── BalanceCommand.java     # /balance, /bal
│   ├── BalanceTopCommand.java  # /balancetop, /baltop
│   ├── EcoCommand.java         # /eco give/take/set/reset/reload
│   ├── PayCommand.java         # /pay
│   ├── SellCommand.java        # /sell hand/all/blocks
│   ├── SetWorthCommand.java    # /setworth
│   ├── ShopCommand.java        # /shop (skeleton)
│   └── WorthCommand.java       # /worth
├── economy/
│   └── EconomyManager.java     # In-memory Map<UUID, Double> balance manager
├── gui/
│   └── ShopGUI.java            # Shop inventory (skeleton)
├── listeners/
│   ├── InventoryListener.java  # GUI click handler (skeleton)
│   └── PlayerJoinListener.java # First-join balance initialization
├── models/
│   ├── PlayerBalance.java      # Record: UUID + double
│   └── ShopItem.java           # POJO: Material, prices, display name
├── services/
│   ├── EconomyService.java     # Service layer (skeleton)
│   └── ShopService.java        # Service layer (skeleton)
├── storage/
│   ├── BalanceRepository.java  # Data access interface
│   └── SQLiteStorage.java      # SQLite implementation (skeleton)
└── utils/
    ├── ItemBuilder.java        # Modern ItemMeta builder (no deprecated APIs)
    └── MessageUtil.java        # Static Adventure message helpers
```

---

## Contributing

Contributions are welcome! Since this project is in early development, the best way to contribute is:

1. **Open an issue** — report bugs, suggest features, or ask questions
2. **Submit a pull request** — fork the repo, make your changes, and open a PR
3. **Test** — deploy the plugin on a test server and report any issues

Please follow the existing code style:
- No deprecated Paper/Bukkit APIs
- Adventure API for all chat messages
- Constructor injection for dependencies
- Clean package separation following SOLID principles

---

## License

This project is open source and available under the MIT License.

---

*Built with [Paper API](https://papermc.io/software/paper) and [Adventure](https://docs.advntr.dev/).*
