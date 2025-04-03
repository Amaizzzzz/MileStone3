package cs5200project;

import cs5200project.model.*;
import cs5200project.dal.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        try {
            resetSchema();
            insertRecords();
        } catch (SQLException e) {
            System.out.print("SQL Exception: ");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void insertRecords() throws SQLException {
        try (Connection cxn = ConnectionManager.getInstance().getConnection()) {
            // Create test data for each table
            System.out.println("Creating test data...");

            // 1. Create Player: Player constructor didn't update, combine first and last name and have region
            Player player = PlayerDao.create(cxn, "John Doe", "john@examples.com", "NA");
            System.out.println("Created player: " + player.getUsername());

            // 2. Create Race
            Race race = RaceDao.create(cxn, "Human");
            System.out.println("Created race: " + race.getRaceName());

            // 3. Create Character
            // Character table in SQL has: characterID, playerID, firstName, lastName, raceID, creationTime, isNewPlayer, currentJobID
            int playerID = player.getPlayerID();
            int raceID = race.getRaceID();
            int currentJobID = 1; // Default to first job
            boolean isNewPlayer = true;
            String firstName = "Alice";
            String lastName = "Smith";
            
            Character character = CharacterDao.create(cxn, playerID, firstName, lastName, raceID, new java.util.Date(), isNewPlayer, currentJobID);
            System.out.println("Created character: " + firstName + " " + lastName);

            // 4. Create CharacterStats
            // Character_Stats in SQL has: characterID, statID, charValue
            int characterID = character.getCharacterID();
            
            // First, create a Statistic
            int statTypeID = 1; // HP stat type
            int statValue = 100;
            Statistic stat = StatisticDao.create(cxn, statTypeID, statValue);
            int statID = stat.getStatID();
            
            // Then associate it with the character
            int charValue = 150; // Character's base HP value
            CharacterStatsDao.create(cxn, characterID, statID, charValue);
            System.out.println("Created character stats for character ID: " + characterID);

            // 5. Create CharacterJob
            // Character_Job in SQL has: characterID, jobID, isUnlocked, XP
            
            // First, create a Job
            String jobName = "Warrior";
            Job warriorJob = JobDao.create(cxn, jobName);
            int jobID = warriorJob.getJobID();
            System.out.println("Created job: " + jobName);
            
            // Then associate it with the character
            boolean isUnlocked = true;
            int xp = 50;
            CharacterJobDao.create(cxn, characterID, jobID, isUnlocked, xp);
            System.out.println("Created character job mapping for character ID: " + characterID);

            // 6. Create Item
            // Item in SQL has: itemID, itemName, itemLevel, maxStackSize, price, quantity
            String itemName = "Iron Sword";
            int itemLevel = 10;
            int maxStackSize = 1;
            double price = 100.0;
            int quantity = 5;
            
            Item item = ItemDao.create(cxn, itemName, itemLevel, maxStackSize, price, quantity);
            int itemID = item.getItemID();
            System.out.println("Created item: " + itemName + " with ID: " + itemID);

            // 7. Create Weapon
            // Weapon in SQL has: itemID, weaponType, gearSlotID, jobID, damage, weaponDurability, rankValue
            
            // First create another item to be associated with the weapon
            String weaponItemName = "Steel Sword";
            Item weaponItem = ItemDao.create(cxn, weaponItemName, itemLevel, maxStackSize, price, quantity);
            int weaponItemID = weaponItem.getItemID();
            
            // Now create the weapon with proper enum values from SQL
            String weaponType = "Sword"; // From ENUM('Sword', 'Hammer', 'Axe', etc.)
            int gearSlotID = 4; // WeaponHand slot
            int damage = 15;
            String weaponDurability = "New"; // From ENUM('New', 'Slight', 'Used', 'Old')
            String rankValue = "Blue"; // From ENUM('White', 'Green', 'Blue', 'Purple', 'Orange', 'Red')
            
            WeaponDao.create(cxn, weaponItemID, weaponType, gearSlotID, jobID, damage, weaponDurability, rankValue);
            System.out.println("Created weapon: " + weaponItemName + " with ID: " + weaponItemID);

            // 8. Create Consumable
            // Consumables in SQL has: itemID, consumableType, consumableDescription, source
            
            // First create an item to be associated with the consumable
            String consumableItemName = "Health Potion";
            int consumableItemLevel = 5;
            int consumableMaxStack = 10;
            double consumablePrice = 50.0;
            int consumableQuantity = 20;
            
            Item consumableItem = ItemDao.create(cxn, consumableItemName, consumableItemLevel, 
                                               consumableMaxStack, consumablePrice, consumableQuantity);
            int consumableItemID = consumableItem.getItemID();
            
            // Now create the consumable with proper enum values from SQL
            String consumableType = "Medicine"; // From ENUM('Groceries', 'Medicine', 'Throwing', 'Material', 'Ammunition')
            String consumableDescription = "Heals a small amount of HP when consumed";
            String source = "Apothecary";
            
            ConsumablesDao.create(cxn, consumableItemID, consumableType, consumableDescription, source);
            System.out.println("Created consumable: " + consumableItemName + " with ID: " + consumableItemID);

            // 9. Create Gear
            // Gear in SQL has: itemID, requiredLevel
            
            // First create an item to be associated with the gear
            String gearItemName = "Iron Helmet";
            int gearItemLevel = 15;
            int gearMaxStack = 1;
            double gearPrice = 75.0;
            int gearQuantity = 3;
            
            Item gearItem = ItemDao.create(cxn, gearItemName, gearItemLevel, 
                                          gearMaxStack, gearPrice, gearQuantity);
            int gearItemID = gearItem.getItemID();
            
            // Now create the gear
            int requiredLevel = 10;
            
            GearDao.create(cxn, gearItemID, requiredLevel);
            System.out.println("Created gear: " + gearItemName + " with ID: " + gearItemID);

            // 11. Create GearSlot
            GearSlot slot = new GearSlot(1, "Head", "Head armor slot");
            GearSlotDao.create(cxn, slot);
            System.out.println("Created gear slot: " + slot.getSlotName());

            // 12. Create GearInstance
            GearInstance gearInstance = new GearInstance(1, 1, 1, 100);
            GearInstanceDao.create(cxn, gearInstance);
            System.out.println("Created gear instance for gear ID: " + gearInstance.getGearId());

            // 13. Create GearJob
            GearJob gearJob = new GearJob(1, 1);
            GearJobDao.create(cxn, gearJob);
            System.out.println("Created gear job mapping for gear ID: " + gearJob.getGearId());

            // 14. Create StatType
            StatType strength = new StatType(1, "Strength", "Physical power");
            StatTypeDao.create(cxn, strength);
            System.out.println("Created stat type: " + strength.getStatName());

            // 15. Create Statistic
            Statistic stat = new Statistic(1, 1, 10);
            StatisticDao.create(cxn, stat);
            System.out.println("Created statistic for stat type ID: " + stat.getStatTypeId());

            // 16. Create GearStatisticBonus
            GearStatisticBonus bonus = new GearStatisticBonus(1, 1, 5);
            GearStatisticBonusDao.create(cxn, bonus);
            System.out.println("Created gear statistic bonus for gear ID: " + bonus.getGearId());

            // 17. Create WeaponStatsBonus
            WeaponStatsBonus weaponBonus = new WeaponStatsBonus(1, 1, 3);
            WeaponStatsBonusDao.create(cxn, weaponBonus);
            System.out.println("Created weapon stats bonus for weapon ID: " + weaponBonus.getWeaponId());

            // 18. Create ConsumablesStatsBonus
            ConsumablesStatsBonus consumableBonus = new ConsumablesStatsBonus(1, 1, 2);
            ConsumablesStatsBonusDao.create(cxn, consumableBonus);
            System.out.println("Created consumable stats bonus for consumable ID: " + consumableBonus.getConsumableId());

            // 19. Create Currency
            Currency gold = new Currency(1, "Gold", "Standard currency", 1.0);
            CurrencyDao.create(cxn, gold);
            System.out.println("Created currency: " + gold.getCurrencyName());

            // 20. Create CharacterCurrency
            CharacterCurrency charCurrency = new CharacterCurrency(1, 1, 1000);
            CharacterCurrencyDao.create(cxn, charCurrency);
            System.out.println("Created character currency for character ID: " + charCurrency.getCharacterId());

            // 21. Create InventorySlot
            InventorySlot invSlot = new InventorySlot(1, 1, 1, 1);
            InventorySlotDao.create(cxn, invSlot);
            System.out.println("Created inventory slot for character ID: " + invSlot.getCharacterId());

            // 22. Create LevelThreshold
            LevelThreshold threshold = new LevelThreshold(1, 1000);
            LevelThresholdDao.create(cxn, threshold);
            System.out.println("Created level threshold for level: " + threshold.getLevel());

            // 22. Create Clan
            // Clan in SQL has: clanID, clanName, raceID
            String clanName = "Warriors Guild";
            // We already have raceID from earlier
            
            int clanID = ClanDao.create(cxn, clanName, race);
            System.out.println("Created clan: " + clanName + " with ID: " + clanID);

            // Test reading records
            System.out.println("\nTesting record retrieval...");

            // Test Player retrieval
            Player retrievedPlayer = PlayerDao.getPlayerFromUserName(cxn, "player1");
            System.out.println("Retrieved player: " + retrievedPlayer.getUserName());

            // Test Race retrieval
            Race retrievedRace = RaceDao.getRaceFromName(cxn, "Human");
            System.out.println("Retrieved race: " + retrievedRace.getRaceName());

            // Test Character retrieval
            List<Character> characters = CharacterDao.getCharactersForPlayer(cxn, 1);
            for (Character c : characters) {
                System.out.println("Retrieved character: " + c.getCharacterName());
            }

            // Continue with other retrieval tests...
        }
    }

    private static void resetSchema() throws SQLException {
        try (Connection cxn = ConnectionManager.getInstance().getConnection()) {
            System.out.println("Dropping existing tables...");
            
            // Drop all tables in reverse order of dependencies
            String[] tables = {
                "Clan", "LevelThreshold", "InventorySlot", "CharacterCurrency",
                "Currency", "ConsumablesStatsBonus", "WeaponStatsBonus",
                "GearStatisticBonus", "Statistic", "StatType", "GearJob",
                "GearInstance", "GearSlot", "Gear", "Consumable", "Weapon",
                "Item", "CharacterJob", "CharacterStats", "Character",
                "Job", "Race", "Player"
            };

            for (String table : tables) {
                cxn.createStatement().executeUpdate("DROP TABLE IF EXISTS " + table);
            }

            System.out.println("Creating new tables...");

            // Create tables in order of dependencies
            cxn.createStatement().executeUpdate("""
                CREATE TABLE Player (
                    playerId INT AUTO_INCREMENT,
                    userName VARCHAR(255) NOT NULL,
                    firstName VARCHAR(255) NOT NULL,
                    lastName VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    CONSTRAINT pk_Player_playerId PRIMARY KEY (playerId),
                    UNIQUE KEY uk_Player_userName (userName)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Race (
                    raceID INT PRIMARY KEY AUTO_INCREMENT,
                    raceName VARCHAR(50) NOT NULL UNIQUE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Job (
                    jobID INT PRIMARY KEY AUTO_INCREMENT, 
                    jobName VARCHAR(50) NOT NULL UNIQUE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE `Character` (
                    characterID INT PRIMARY KEY AUTO_INCREMENT,
                    playerID INT NOT NULL,
                    firstName VARCHAR(50) NOT NULL,
                    lastName VARCHAR(50) NOT NULL,
                    raceID INT NOT NULL,
                    creationTime DATETIME DEFAULT CURRENT_TIMESTAMP,
                    isNewPlayer BOOLEAN NOT NULL DEFAULT TRUE,
                    currentJobID INT NOT NULL DEFAULT 1,
                    CONSTRAINT fk_character_player FOREIGN KEY (playerID) REFERENCES Player(playerID)
                        ON DELETE RESTRICT ON UPDATE CASCADE,
                    CONSTRAINT fk_character_race FOREIGN KEY (raceID) REFERENCES Race(raceID)
                        ON DELETE RESTRICT ON UPDATE CASCADE,
                    CONSTRAINT fk_character_currentJob FOREIGN KEY (currentJobID) REFERENCES Job(jobID)
                        ON DELETE RESTRICT ON UPDATE CASCADE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE CharacterStats (
                    characterID INT NOT NULL,
                    statID INT NOT NULL,
                    value INT UNSIGNED NOT NULL DEFAULT 0,
                    CONSTRAINT pk_CharacterStatistic_characterID_statID PRIMARY KEY (characterID, statID),
                    CONSTRAINT fk_CharacterStatistic_characterID FOREIGN KEY (characterID) REFERENCES `Character`(characterID)
                        ON DELETE CASCADE,
                    CONSTRAINT fk_CharacterStatistic_statID FOREIGN KEY (statID) REFERENCES Statistic(statID)
                        ON DELETE CASCADE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE CharacterJob (
                    characterID INT NOT NULL,
                    jobID INT NOT NULL,
                    isUnlocked BOOLEAN DEFAULT FALSE,
                    XP INT UNSIGNED NULL DEFAULT 0,
                    CONSTRAINT pk_character_job PRIMARY KEY (characterID, jobID),
                    CONSTRAINT fk_character_job_character FOREIGN KEY (characterID) REFERENCES `Character`(characterID)
                        ON DELETE RESTRICT ON UPDATE CASCADE,
                    CONSTRAINT fk_character_job_job FOREIGN KEY (jobID) REFERENCES Job(jobID)
                        ON DELETE RESTRICT ON UPDATE CASCADE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Item (
                    itemID INT PRIMARY KEY AUTO_INCREMENT,
                    itemName VARCHAR(255) NOT NULL UNIQUE,
                    itemLevel INT UNSIGNED NOT NULL,
                    maxStackSize INT UNSIGNED DEFAULT 1,
                    price DECIMAL(10,2) UNSIGNED NOT NULL,
                    quantity INT UNSIGNED NOT NULL
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Weapon (
                    itemID INT PRIMARY KEY,
                    weaponType ENUM('Sword', 'Hammer', 'Axe', 'Spear', 'Stick', 'Bow', 'Crossbow', 'Wand', 'Gun', 'Shield', 'Knife', 'Dart', 'Fist Gloves') NOT NULL,
                    gearSlotID INT NOT NULL,
                    jobID INT NOT NULL,
                    damage INT UNSIGNED NOT NULL DEFAULT 0,
                    weaponDurability ENUM('New', 'Slight', 'Used', 'Old') NOT NULL DEFAULT 'New',
                    rankValue ENUM('White', 'Green', 'Blue', 'Purple', 'Orange', 'Red') NOT NULL DEFAULT 'White',
                    CONSTRAINT fk_Weapon_itemID FOREIGN KEY (itemID) REFERENCES Item(itemID) 
                        ON DELETE CASCADE,
                    CONSTRAINT fk_Weapon_gearSlotID FOREIGN KEY (gearSlotID) REFERENCES GearSlot(slotID) 
                        ON DELETE CASCADE,
                    CONSTRAINT fk_Weapon_jobID FOREIGN KEY (jobID) REFERENCES Job(jobID) 
                        ON DELETE CASCADE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Consumable (
                    itemID INT PRIMARY KEY,
                    consumableType ENUM('Groceries', 'Medicine', 'Throwing', 'Material', 'Ammunition') NOT NULL,
                    consumableDescription TEXT NOT NULL,
                    source VARCHAR(255) NOT NULL,
                    CONSTRAINT fk_Consumables_itemID FOREIGN KEY (itemID) REFERENCES Item(itemID) 
                        ON DELETE CASCADE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE GearSlot (
                    slotID INT PRIMARY KEY AUTO_INCREMENT,
                    slotName VARCHAR(50) NOT NULL UNIQUE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Gear (
                    itemID INT PRIMARY KEY,
                    requiredLevel INT UNSIGNED NOT NULL,
                    CONSTRAINT fk_Gear_itemID FOREIGN KEY (itemID) REFERENCES Item(itemID) 
                        ON DELETE CASCADE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE GearInstance (
                    gearInstanceID INT PRIMARY KEY AUTO_INCREMENT,
                    itemID INT NOT NULL,
                    durability TINYINT UNSIGNED NOT NULL DEFAULT 100,
                    characterID INT,
                    CONSTRAINT fk_GearInstance_itemID FOREIGN KEY (itemID) REFERENCES Gear(itemID)
                        ON DELETE CASCADE,
                    CONSTRAINT fk_GearInstance_characterID FOREIGN KEY (characterID) REFERENCES `Character`(characterID)
                        ON DELETE SET NULL,
                    CONSTRAINT chk_GearInstance_durability CHECK (durability BETWEEN 0 AND 100)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE GearJob (
                    itemID INT NOT NULL,
                    jobID INT NOT NULL,
                    CONSTRAINT pk_GearJob_itemID_jobID PRIMARY KEY (itemID, jobID),
                    CONSTRAINT fk_GearJob_itemID FOREIGN KEY (itemID) REFERENCES Gear(itemID) 
                        ON DELETE CASCADE,
                    CONSTRAINT fk_GearJob_jobID FOREIGN KEY (jobID) REFERENCES Job(jobID) 
                        ON DELETE CASCADE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE StatType (
                    statTypeID INT PRIMARY KEY AUTO_INCREMENT,
                    statType VARCHAR(50) NOT NULL UNIQUE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Statistic (
                    statID INT PRIMARY KEY AUTO_INCREMENT, 
                    statTypeID INT NOT NULL,
                    statValue INT UNSIGNED DEFAULT 0,
                    CONSTRAINT fk_Statistic_statTypeID FOREIGN KEY (statTypeID) REFERENCES StatType(statTypeID)
                        ON DELETE CASCADE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE GearStatisticBonus (
                    itemID INT NOT NULL,
                    statID INT NOT NULL,
                    bonusValue INT UNSIGNED NOT NULL DEFAULT 0,
                    CONSTRAINT pk_GearStatisticBonus_itemID_statID PRIMARY KEY (itemID, statID),
                    CONSTRAINT fk_GearStatisticBonus_itemID FOREIGN KEY (itemID) REFERENCES Gear(itemID) 
                        ON DELETE CASCADE,
                    CONSTRAINT fk_GearStatisticBonus_statID FOREIGN KEY (statID) REFERENCES Statistic(statID) 
                        ON DELETE CASCADE
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE WeaponStatsBonus (
                    weaponId INT NOT NULL,
                    statTypeId INT NOT NULL,
                    bonusValue INT NOT NULL,
                    CONSTRAINT pk_WeaponStatsBonus_weaponId_statTypeId PRIMARY KEY (weaponId, statTypeId),
                    CONSTRAINT fk_WeaponStatsBonus_weaponId FOREIGN KEY (weaponId) REFERENCES Weapon(itemID),
                    CONSTRAINT fk_WeaponStatsBonus_statTypeId FOREIGN KEY (statTypeId) REFERENCES StatType(statTypeId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE ConsumablesStatsBonus (
                    consumableId INT NOT NULL,
                    statTypeId INT NOT NULL,
                    bonusValue INT NOT NULL,
                    CONSTRAINT pk_ConsumablesStatsBonus_consumableId_statTypeId PRIMARY KEY (consumableId, statTypeId),
                    CONSTRAINT fk_ConsumablesStatsBonus_consumableId FOREIGN KEY (consumableId) REFERENCES Consumable(itemID),
                    CONSTRAINT fk_ConsumablesStatsBonus_statTypeId FOREIGN KEY (statTypeId) REFERENCES StatType(statTypeId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Currency (
                    currencyId INT AUTO_INCREMENT,
                    currencyName VARCHAR(255) NOT NULL,
                    description TEXT,
                    exchangeRate DECIMAL(10,2) NOT NULL,
                    CONSTRAINT pk_Currency_currencyId PRIMARY KEY (currencyId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE CharacterCurrency (
                    characterId INT NOT NULL,
                    currencyId INT NOT NULL,
                    currentAmount INT NOT NULL,
                    isCurrent BOOLEAN NOT NULL DEFAULT TRUE,
                    CONSTRAINT pk_CharacterCurrency_characterId_currencyId PRIMARY KEY (characterId, currencyId),
                    CONSTRAINT fk_CharacterCurrency_characterId FOREIGN KEY (characterId) REFERENCES Character(characterID),
                    CONSTRAINT fk_CharacterCurrency_currencyId FOREIGN KEY (currencyId) REFERENCES Currency(currencyID)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE InventorySlot (
                    characterID INT NOT NULL,
                    itemID INT NOT NULL,
                    slotNum TINYINT UNSIGNED NOT NULL,
                    quantity TINYINT UNSIGNED NOT NULL DEFAULT 1,
                    CONSTRAINT pk_InventorySlot_characterID_slotNum PRIMARY KEY (characterID, slotNum),
                    CONSTRAINT fk_InventorySlot_characterID FOREIGN KEY (characterID) REFERENCES `Character`(characterID)
                        ON DELETE CASCADE,
                    CONSTRAINT fk_InventorySlot_itemID FOREIGN KEY (itemID) REFERENCES Item(itemID)
                        ON DELETE CASCADE,
                    CONSTRAINT chk_InventorySlot_slotNum CHECK (slotNum BETWEEN 1 AND 100),
                    CONSTRAINT chk_InventorySlot_quantity CHECK (quantity > 0)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE LevelThreshold (
                    level TINYINT UNSIGNED PRIMARY KEY,
                    experienceRequired INT UNSIGNED NOT NULL,
                    CONSTRAINT chk_LevelThreshold_level CHECK (level BETWEEN 1 AND 99),
                    CONSTRAINT chk_LevelThreshold_experienceRequired CHECK (experienceRequired >= 0)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Clan (
                    clanID INT PRIMARY KEY AUTO_INCREMENT,
                    clanName VARCHAR(50) NOT NULL UNIQUE,
                    raceID INT NOT NULL,
                    CONSTRAINT fk_Clan_raceID FOREIGN KEY (raceID) REFERENCES Race(raceID)
                        ON UPDATE CASCADE ON DELETE RESTRICT
                );"""
            );
            
        }
    }
} 