-- MySQL Script for GNS
-- Created on: 03/06/19 22:02:41
-- Model: GPS Notebook System   Version: 1.0
-- Author: Ralph Florent <r.florent@jacobs-university.de>, Asad Ahmed <as.ahmed@jacobs-university.de>

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- START Schema `gns`
-- -----------------------------------------------------

-- -----------------------------------------------------
-- CREATE Schema `gns`
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gns` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `gns` ;

-- -----------------------------------------------------
-- Table `gns`.`notebooks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gns`.`notebooks` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `note_id` VARCHAR(10) NOT NULL,
  `description` VARCHAR(250) NOT NULL,
  `note` TEXT NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `gns_date` DATETIME NOT NULL,
  `created_on` DATETIME NULL,
  `created_by` VARCHAR(120) NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- INSERT Sample data into Schema `gns`
-- NOTES: Run these lines above for sample-purpose only
-- -----------------------------------------------------
INSERT INTO `gns`.`notebooks` (
  `note_id`, 
  `description`, 
  `note`, 
  `latitude`, 
  `longitude`, 
  `gns_date`, 
  `created_on`, 
  `created_by`
)
VALUES( 
  'AA00-00001', 
  'Description of the sample note', 
  'Very very very long note...', 
  53.1042761, 
  8.8518051, 
  NOW(), 
  NOW(), 
  'Ralph Florent @ralflorent' 
);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
-- -----------------------------------------------------
-- END Schema `gns`
-- -----------------------------------------------------