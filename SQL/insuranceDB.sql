Drop database IF EXISTS InsuranceDB;
CREATE DATABASE InsuranceDB;

USE InsuranceDB;

CREATE TABLE InsuranceType
(
  insuranceType VARCHAR(80) NOT NULL PRIMARY KEY
);
INSERT INTO InsuranceType VALUES ('Car'),('Vehicle');
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
  FOREIGN KEY (policyNumber) REFERENCES Policy(policyNumber) ON DELETE CASCADE

);

CREATE TABLE ClaimImagePath
(
  claimImagePath VARCHAR(255),
  policyNumber INT NOT NULL ,
  FOREIGN KEY (policyNumber) REFERENCES Policy(policyNumber) ON DELETE CASCADE

);

INSERT INTO Clients (InsuranceDB.Clients.clientName,InsuranceDB.Clients.clientNumber) VALUES ('Ramy Emad Malek', '01226140201'),('Walid Hassan','01113438653');
/*INSERT INTO Policy (InsuranceDB.Policy.agentName,InsuranceDB.Policy.insuranceCompany,InsuranceDB.Policy.insuranceType,InsuranceDB.Policy.clientID,InsuranceDB.Policy.policyNumber,InsuranceDB.Policy.grossPremuim,InsuranceDB.Policy.expiryDate,InsuranceDB.Policy.currency,InsuranceDB.Policy.collective) VALUES
  ('Ahmed Azmy','AIG','Car',1,209728,3498.6,CURDATE(),'EGP','Cache'),('Hassan Saied','ElMotahda','Vehicle',2,209736,4076.6,CURDATE(),'EGP','Cache');*/
INSERT INTO Policy VALUES ('Hassan','AIG','Car','Egypt',1,209728,3047.5,1234.5,1234.5,1234.5,0.2,1572,CURDATE(),12345,'EGP','Cache','asdadasd','Collected',1234,NULL);


