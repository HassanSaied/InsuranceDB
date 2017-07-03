Drop database IF EXISTS InsuranceDB;
CREATE DATABASE InsuranceDB;

USE InsuranceDB;

CREATE TABLE InsuranceType
(
  insuranceType VARCHAR(80) NOT NULL PRIMARY KEY
);

CREATE TABLE PolicyStatus
(
  policyStatus VARCHAR(80) NOT NULL PRIMARY KEY
);

INSERT INTO PolicyStatus VALUES ('Pending');
INSERT INTO PolicyStatus VALUES ('Collected');
INSERT INTO PolicyStatus VALUES ('Cancelled');

CREATE TABLE Clients
(
  clientID INT  NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  clientName VARCHAR(80) ,
  clientNumber VARCHAR(80)
);

CREATE TABLE Policy
(
  agentName VARCHAR(80) NOT NULL ,
  insuranceCompany VARCHAR(80) NOT NULL ,
  insuranceType VARCHAR(80) NOT NULL , /*LOP*/
  beneficiary VARCHAR(80) ,
  clientID INT ,
  policyNumber INT NOT NULL PRIMARY KEY,
  grossPremuim FLOAT NOT NULL ,
  specialDiscount FLOAT ,
  netPremuim FLOAT ,
  grossCommission FLOAT ,		/*password*/
  taxes FLOAT,		/*20% or 22.5%*/
  netCommission FLOAT, /*commission - taxes*/
  expiryDate Date ,
  sumInssured FLOAT ,
  currency ENUM('EGP','USD','EUR') ,
  collective ENUM('Cache','Check','None') ,
  collectiveImagePath VARCHAR(255),
  policyStatus VARCHAR(80) ,
  paidClaims FLOAT , /*checks we kda*/
  indoresmentNumber INT,
  FOREIGN KEY (policyStatus) REFERENCES PolicyStatus(policyStatus),
  FOREIGN KEY (insuranceType) REFERENCES InsuranceType(insuranceType),
  FOREIGN KEY (clientID) REFERENCES Clients(clientID),
  FOREIGN KEY (indoresmentNumber) REFERENCES Policy (policyNumber) ON DELETE SET NULL

);

DELIMITER $$
  CREATE TRIGGER policyUpdateTrigger BEFORE UPDATE on Policy
  FOR EACH ROW BEGIN
  IF(NEW.indoresmentNumber = -1 ) THEN SET NEW.indoresmentNumber = NULL; 
  END IF;
  END ; $$
DELIMITER ;


DELIMITER $$
  CREATE TRIGGER policyInsertTrigger BEFORE INSERT on Policy
  FOR EACH ROW BEGIN
  IF(NEW.indoresmentNumber = -1 ) THEN SET NEW.indoresmentNumber = NULL; 
  END IF;
  END ; $$
DELIMITER ;

CREATE TABLE PolicyImagePath
(
  policyImagePath VARCHAR(255),
  policyNumber INT NOT NULL ,
  FOREIGN KEY (policyNumber) REFERENCES Policy(policyNumber)

);

CREATE TABLE ClaimImagePath
(
  claimImagePath VARCHAR(255),
  policyNumber INT NOT NULL ,
  FOREIGN KEY (policyNumber) REFERENCES Policy(policyNumber)

);



