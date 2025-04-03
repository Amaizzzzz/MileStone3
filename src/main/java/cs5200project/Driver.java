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

            // 1. Create Player
            Player player = new Player("player1", "John", "Doe", "john@example.com", "password123");
            PlayerDao.create(cxn, player);
            System.out.println("Created player: " + player.getUserName());

            // 2. Create Race
            Race race = new Race("Human", "A versatile race", 5, 5, 5, 5, 5, 5);
            RaceDao.create(cxn, race);
            System.out.println("Created race: " + race.getRaceName());

            // 3. Create Character
            Character character = new Character(1, "Warrior1", 1, 1, 1);
            CharacterDao.create(cxn, character);
            System.out.println("Created character: " + character.getCharacterName());

            // 4. Create CharacterStats
            CharacterStat stats = new CharacterStat(1);
            CharacterStatsDao.create(cxn, stats);
            System.out.println("Created character stats for character ID: " + stats.getCharacterId());

            // 5. Create CharacterJob
            CharacterJob job = new CharacterJob(1, 1, true, 0);
            CharacterJobDao.create(cxn, job);
            System.out.println("Created character job for character ID: " + job.getCharacterID());

            // 6. Create Job
            Job warriorJob = new Job("Warrior", "A melee combat specialist");
            JobDao.create(cxn, warriorJob);
            System.out.println("Created job: " + warriorJob.getJobName());

            // 7. Create Item
            Item item = new Item(1, "Iron Sword", 1, 1, 100.0, 1);
            ItemDao.create(cxn, item);
            System.out.println("Created item: " + item.getItemName());

            // 8. Create Weapon
            Weapon sword = new Weapon(1, "Iron Sword", 1, 1, 100.0, 1, 1, 10, 1, "Sword", 1, "Warrior");
            WeaponDao.create(cxn, sword);
            System.out.println("Created weapon: " + sword.getItemName());

            // 9. Create Consumable
            Consumable potion = new Consumable(2, "Health Potion", 1, 10, 50.0, 1, 1, 50, 0, 0, "Healing", true, 30);
            ConsumablesDao.create(cxn, potion);
            System.out.println("Created consumable: " + potion.getItemName());

            // 10. Create Gear
            Gear gear = new Gear(3, "Iron Helmet", 1, 1, 1, 75.0, 1);
            GearDao.create(cxn, gear);
            System.out.println("Created gear: " + gear.getItemName());

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

            // 23. Create Clan
            Clan clan = new Clan(1, "Warriors Guild", "A guild for warriors", 1);
            ClanDao.create(cxn, clan);
            System.out.println("Created clan: " + clan.getClanName());

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
                    raceId INT AUTO_INCREMENT,
                    raceName VARCHAR(255) NOT NULL,
                    description TEXT,
                    strengthBonus INT NOT NULL,
                    dexterityBonus INT NOT NULL,
                    constitutionBonus INT NOT NULL,
                    intelligenceBonus INT NOT NULL,
                    wisdomBonus INT NOT NULL,
                    charismaBonus INT NOT NULL,
                    CONSTRAINT pk_Race_raceId PRIMARY KEY (raceId),
                    UNIQUE KEY uk_Race_raceName (raceName)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Job (
                    jobId INT AUTO_INCREMENT,
                    jobName VARCHAR(255) NOT NULL,
                    description TEXT,
                    CONSTRAINT pk_Job_jobId PRIMARY KEY (jobId),
                    UNIQUE KEY uk_Job_jobName (jobName)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Character (
                    characterId INT AUTO_INCREMENT,
                    characterName VARCHAR(255) NOT NULL,
                    playerId INT NOT NULL,
                    raceId INT NOT NULL,
                    level INT NOT NULL,
                    CONSTRAINT pk_Character_characterId PRIMARY KEY (characterId),
                    CONSTRAINT fk_Character_playerId FOREIGN KEY (playerId) REFERENCES Player(playerId),
                    CONSTRAINT fk_Character_raceId FOREIGN KEY (raceId) REFERENCES Race(raceId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE CharacterStats (
                    characterId INT NOT NULL,
                    strength INT NOT NULL,
                    dexterity INT NOT NULL,
                    constitution INT NOT NULL,
                    intelligence INT NOT NULL,
                    wisdom INT NOT NULL,
                    charisma INT NOT NULL,
                    CONSTRAINT pk_CharacterStats_characterId PRIMARY KEY (characterId),
                    CONSTRAINT fk_CharacterStats_characterId FOREIGN KEY (characterId) REFERENCES Character(characterId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE CharacterJob (
                    characterId INT NOT NULL,
                    jobId INT NOT NULL,
                    isPrimary BOOLEAN NOT NULL,
                    jobLevel INT NOT NULL,
                    CONSTRAINT pk_CharacterJob_characterId_jobId PRIMARY KEY (characterId, jobId),
                    CONSTRAINT fk_CharacterJob_characterId FOREIGN KEY (characterId) REFERENCES Character(characterId),
                    CONSTRAINT fk_CharacterJob_jobId FOREIGN KEY (jobId) REFERENCES Job(jobId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Item (
                    itemId INT AUTO_INCREMENT,
                    itemName VARCHAR(255) NOT NULL,
                    itemType INT NOT NULL,
                    rarity INT NOT NULL,
                    value DECIMAL(10,2) NOT NULL,
                    levelRequirement INT NOT NULL,
                    CONSTRAINT pk_Item_itemId PRIMARY KEY (itemId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Weapon (
                    itemId INT NOT NULL,
                    itemName VARCHAR(255) NOT NULL,
                    itemType INT NOT NULL,
                    rarity INT NOT NULL,
                    value DECIMAL(10,2) NOT NULL,
                    levelRequirement INT NOT NULL,
                    damage INT NOT NULL,
                    attackSpeed INT NOT NULL,
                    weaponTypeId INT NOT NULL,
                    weaponType VARCHAR(255) NOT NULL,
                    jobRequirementId INT NOT NULL,
                    jobRequirement VARCHAR(255) NOT NULL,
                    CONSTRAINT pk_Weapon_itemId PRIMARY KEY (itemId),
                    CONSTRAINT fk_Weapon_itemId FOREIGN KEY (itemId) REFERENCES Item(itemId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Consumable (
                    itemId INT NOT NULL,
                    itemName VARCHAR(255) NOT NULL,
                    itemType INT NOT NULL,
                    rarity INT NOT NULL,
                    value DECIMAL(10,2) NOT NULL,
                    levelRequirement INT NOT NULL,
                    effectType INT NOT NULL,
                    effectValue INT NOT NULL,
                    duration INT NOT NULL,
                    cooldown INT NOT NULL,
                    effectCategory VARCHAR(255) NOT NULL,
                    isStackable BOOLEAN NOT NULL,
                    maxStackSize INT NOT NULL,
                    CONSTRAINT pk_Consumable_itemId PRIMARY KEY (itemId),
                    CONSTRAINT fk_Consumable_itemId FOREIGN KEY (itemId) REFERENCES Item(itemId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE GearSlot (
                    slotId INT AUTO_INCREMENT,
                    slotName VARCHAR(255) NOT NULL,
                    description TEXT,
                    CONSTRAINT pk_GearSlot_slotId PRIMARY KEY (slotId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Gear (
                    itemId INT NOT NULL,
                    itemName VARCHAR(255) NOT NULL,
                    itemType INT NOT NULL,
                    rarity INT NOT NULL,
                    slotId INT NOT NULL,
                    value DECIMAL(10,2) NOT NULL,
                    levelRequirement INT NOT NULL,
                    CONSTRAINT pk_Gear_itemId PRIMARY KEY (itemId),
                    CONSTRAINT fk_Gear_itemId FOREIGN KEY (itemId) REFERENCES Item(itemId),
                    CONSTRAINT fk_Gear_slotId FOREIGN KEY (slotId) REFERENCES GearSlot(slotId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE GearInstance (
                    instanceId INT AUTO_INCREMENT,
                    gearId INT NOT NULL,
                    characterId INT NOT NULL,
                    durability INT NOT NULL,
                    CONSTRAINT pk_GearInstance_instanceId PRIMARY KEY (instanceId),
                    CONSTRAINT fk_GearInstance_gearId FOREIGN KEY (gearId) REFERENCES Gear(itemId),
                    CONSTRAINT fk_GearInstance_characterId FOREIGN KEY (characterId) REFERENCES Character(characterId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE GearJob (
                    gearId INT NOT NULL,
                    jobId INT NOT NULL,
                    CONSTRAINT pk_GearJob_gearId_jobId PRIMARY KEY (gearId, jobId),
                    CONSTRAINT fk_GearJob_gearId FOREIGN KEY (gearId) REFERENCES Gear(itemId),
                    CONSTRAINT fk_GearJob_jobId FOREIGN KEY (jobId) REFERENCES Job(jobId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE StatType (
                    statTypeId INT AUTO_INCREMENT,
                    statName VARCHAR(255) NOT NULL,
                    description TEXT,
                    CONSTRAINT pk_StatType_statTypeId PRIMARY KEY (statTypeId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Statistic (
                    statId INT AUTO_INCREMENT,
                    statTypeId INT NOT NULL,
                    value INT NOT NULL,
                    CONSTRAINT pk_Statistic_statId PRIMARY KEY (statId),
                    CONSTRAINT fk_Statistic_statTypeId FOREIGN KEY (statTypeId) REFERENCES StatType(statTypeId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE GearStatisticBonus (
                    gearId INT NOT NULL,
                    statTypeId INT NOT NULL,
                    bonusValue INT NOT NULL,
                    CONSTRAINT pk_GearStatisticBonus_gearId_statTypeId PRIMARY KEY (gearId, statTypeId),
                    CONSTRAINT fk_GearStatisticBonus_gearId FOREIGN KEY (gearId) REFERENCES Gear(itemId),
                    CONSTRAINT fk_GearStatisticBonus_statTypeId FOREIGN KEY (statTypeId) REFERENCES StatType(statTypeId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE WeaponStatsBonus (
                    weaponId INT NOT NULL,
                    statTypeId INT NOT NULL,
                    bonusValue INT NOT NULL,
                    CONSTRAINT pk_WeaponStatsBonus_weaponId_statTypeId PRIMARY KEY (weaponId, statTypeId),
                    CONSTRAINT fk_WeaponStatsBonus_weaponId FOREIGN KEY (weaponId) REFERENCES Weapon(itemId),
                    CONSTRAINT fk_WeaponStatsBonus_statTypeId FOREIGN KEY (statTypeId) REFERENCES StatType(statTypeId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE ConsumablesStatsBonus (
                    consumableId INT NOT NULL,
                    statTypeId INT NOT NULL,
                    bonusValue INT NOT NULL,
                    CONSTRAINT pk_ConsumablesStatsBonus_consumableId_statTypeId PRIMARY KEY (consumableId, statTypeId),
                    CONSTRAINT fk_ConsumablesStatsBonus_consumableId FOREIGN KEY (consumableId) REFERENCES Consumable(itemId),
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
                    amount INT NOT NULL,
                    CONSTRAINT pk_CharacterCurrency_characterId_currencyId PRIMARY KEY (characterId, currencyId),
                    CONSTRAINT fk_CharacterCurrency_characterId FOREIGN KEY (characterId) REFERENCES Character(characterId),
                    CONSTRAINT fk_CharacterCurrency_currencyId FOREIGN KEY (currencyId) REFERENCES Currency(currencyId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE InventorySlot (
                    slotId INT AUTO_INCREMENT,
                    characterId INT NOT NULL,
                    itemId INT NOT NULL,
                    quantity INT NOT NULL,
                    CONSTRAINT pk_InventorySlot_slotId PRIMARY KEY (slotId),
                    CONSTRAINT fk_InventorySlot_characterId FOREIGN KEY (characterId) REFERENCES Character(characterId),
                    CONSTRAINT fk_InventorySlot_itemId FOREIGN KEY (itemId) REFERENCES Item(itemId)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE LevelThreshold (
                    level INT NOT NULL,
                    experienceRequired INT NOT NULL,
                    CONSTRAINT pk_LevelThreshold_level PRIMARY KEY (level)
                );"""
            );

            cxn.createStatement().executeUpdate("""
                CREATE TABLE Clan (
                    clanId INT AUTO_INCREMENT,
                    clanName VARCHAR(255) NOT NULL,
                    description TEXT,
                    leaderId INT NOT NULL,
                    CONSTRAINT pk_Clan_clanId PRIMARY KEY (clanId),
                    CONSTRAINT fk_Clan_leaderId FOREIGN KEY (leaderId) REFERENCES Character(characterId)
                );"""
            );
            
        }
    }
} 