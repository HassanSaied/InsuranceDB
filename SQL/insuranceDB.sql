DROP DATABASE IF EXISTS InsuranceDB;
CREATE DATABASE InsuranceDB;

USE InsuranceDB;

CREATE TABLE InsuranceType
(
  insuranceType VARCHAR(80) NOT NULL PRIMARY KEY
);
INSERT INTO InsuranceType VALUES ('Car'), ('Vehicle');


CREATE TABLE PolicyStatus
(
  policyStatus VARCHAR(80) NOT NULL PRIMARY KEY
);
INSERT INTO PolicyStatus VALUES ('Pending');
INSERT INTO PolicyStatus VALUES ('Collected');
INSERT INTO PolicyStatus VALUES ('Cancelled');

CREATE TABLE Clients
(
  clientID     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  clientName   VARCHAR(80),
  clientNumber VARCHAR(80)
);

CREATE TABLE Agents
(
  agentName VARCHAR(80) PRIMARY KEY NOT NULL
);
INSERT INTO Agents (agentName) VALUES ('Hassan');

CREATE TABLE Policy
(
  agentName           VARCHAR(80),
  insuranceCompany    VARCHAR(80),
  insuranceType       VARCHAR(80), /*LOP*/
  beneficiary         VARCHAR(80),
  clientID            INT,
  policyNumber        VARCHAR(80) NOT NULL PRIMARY KEY,
  grossPremuim        FLOAT,
  specialDiscount     FLOAT,
  netPremuim          FLOAT,
  grossCommission     FLOAT, /*password*/
  taxes               FLOAT, /*20% or 22.5%*/
  netCommission       FLOAT, /*commission - taxes*/
  expiryDate          DATE,
  sumInssured         FLOAT,
  currency            ENUM ('EGP', 'USD', 'EUR'),
  collective          ENUM ('Cache', 'Check', 'None'),
  collectiveImagePath VARCHAR(255),
  policyStatus        VARCHAR(80),
  paidClaims          FLOAT, /*checks we kda*/
  issuanceDate        DATE,
  hasEndoresments     BOOLEAN DEFAULT FALSE,
  chassisNumber       VARCHAR(20),
  motorNumber         VARCHAR(20),
  plateNumber         VARCHAR(20),
  FOREIGN KEY (agentName) REFERENCES Agents (agentName),
  FOREIGN KEY (policyStatus) REFERENCES PolicyStatus (policyStatus),
  FOREIGN KEY (insuranceType) REFERENCES InsuranceType (insuranceType),
  FOREIGN KEY (clientID) REFERENCES Clients (clientID)

);

CREATE TABLE ClaimImagePath
(
  claimImagePath VARCHAR(255),
  policyNumber   VARCHAR(80) NOT NULL,
  FOREIGN KEY (policyNumber) REFERENCES Policy (policyNumber)
    ON DELETE CASCADE

);

CREATE TABLE PolicyImagePath
(
  policyImagePath VARCHAR(255),
  policyNumber    VARCHAR(80) NOT NULL,
  FOREIGN KEY (policyNumber) REFERENCES Policy (policyNumber)
    ON DELETE CASCADE

);

DELIMITER &&;
CREATE TRIGGER clientAutoDelete
AFTER DELETE ON Policy
FOR EACH ROW
  BEGIN
    DECLARE clientCount INT;
    SELECT COUNT(*)
    FROM Policy
    WHERE clientID = OLD.clientID
    INTO clientCount;
    IF (clientCount = 0)
    THEN
      DELETE FROM Clients
      WHERE clientID = old.clientID;
    END IF;
  END;
DELIMITER ;
DELIMITER &&;


CREATE TRIGGER pathAutoDelete
BEFORE UPDATE ON Policy
FOR EACH ROW
  BEGIN
    DELETE FROM PolicyImagePath
    WHERE PolicyImagePath.policyNumber = NEW.policyNumber;
    DELETE FROM ClaimImagePath
    WHERE ClaimImagePath.policyNumber = NEW.policyNumber;
  END;
DELIMITER ;

CREATE TABLE Endorsement
(
  policyNumber      VARCHAR(80),
  endorsementNumber VARCHAR(80),
  issuanceDate      DATE,
  grossPremium      FLOAT,
  specialDiscount   FLOAT,
  netPremium        FLOAT,
  grossCommission   FLOAT,
  taxes             FLOAT,
  netCommission     FLOAT,

  PRIMARY KEY (policyNumber, endorsementNumber),
  FOREIGN KEY (policyNumber) REFERENCES Policy (policyNumber)
    ON DELETE CASCADE

);

CREATE TABLE EndorsementImagePath
(
  policyNumber      VARCHAR(80),
  endorsementNumber VARCHAR(80),
  imagePath         VARCHAR(255),
  FOREIGN KEY (policyNumber, endorsementNumber) REFERENCES Endorsement (policyNumber, endorsementNumber)
    ON DELETE CASCADE

);


DELIMITER &&;
CREATE TRIGGER EndorsementPathAutoDelete
BEFORE UPDATE ON Endorsement
FOR EACH ROW
  BEGIN
    DELETE FROM EndorsementImagePath
    WHERE EndorsementImagePath.policyNumber = NEW.policyNumber AND
          EndorsementImagePath.endorsementNumber = NEW.endorsementNumber;
  END;
DELIMITER ;

DELIMITER &&;
CREATE TRIGGER hasEndorsementsUpdate
AFTER INSERT ON Endorsement
FOR EACH ROW
  BEGIN
    UPDATE Policy
    SET hasEndoresments = TRUE
    WHERE policyNumber = NEW.policyNumber;
  END &&;
DELIMITER ;

DELIMITER &&
CREATE TRIGGER hasEndorsementsDelete
AFTER DELETE ON Endorsement
FOR EACH ROW
  BEGIN
    DECLARE endorsementsCount INTEGER;
    SELECT count(*)
    FROM Endorsement
    WHERE policyNumber = OLD.policyNumber
    INTO endorsementsCount;
    IF (endorsementsCount = 0)
    THEN
      UPDATE Policy
      SET hasEndoresments = FALSE
      WHERE policyNumber = OLD.policyNumber;
    END IF;
  END &&

INSERT INTO Clients (InsuranceDB.Clients.clientName, InsuranceDB.Clients.clientNumber)
VALUES ('Ramy Emad Malek', '01226140201'), ('Walid Hassan', '01113438653');

INSERT INTO Policy VALUES
  ('Hassan', 'AIG', 'Car', 'Egypt', 1, '209728', 3047.5, 1234.5, 1234.5, 1234.5, 0.2, 1572, CURDATE(), 12345, 'EGP',
                                                                                      'Cache', 'asdadasd', 'Collected',
                                                                                      1234, NULL, FALSE),
  ('Hassan', 'AIG', 'Car', 'Egypt', 1, '209729', 3047.5, 1234.5, 1234.5, 1234.5, 0.2, 1572, CURDATE(), 12345, 'EGP',
                                                                                      'Cache', 'asdadasd', 'Collected',
                                                                                      1234, NULL, FALSE),
  ('Hassan', 'AIG', 'Car', 'Egypt', 1, '209780', 3047.5, 1234.5, 1234.5, 1234.5, 0.2, 1572, CURDATE(), 12345, 'EGP',
                                                                                      'Cache', 'asdadasd', 'Collected',
                                                                                      1234, NULL, FALSE)
  , ('Hassan', 'AIG', 'Car', 'Egypt', 1, '209724', 3047.5, 1234.5, 1234.5, 1234.5, 0.2, 1572, CURDATE(), 12345, 'EGP',
                                                                                        'Cache', 'asdadasd',
                                                                                        'Collected', 1234, NULL, FALSE),
  ('Hassan', 'AIG', 'Car', 'Egypt', 1, '209723', 3047.5, 1234.5, 1234.5, 1234.5, 0.2, 1572, CURDATE(), 12345, 'EGP',
                                                                                      'Cache', 'asdadasd', 'Collected',
                                                                                      1234, NULL, FALSE),
  ('Hassan', 'AIG', 'Car', 'Egypt', 1, '209725', 3047.5, 1234.5, 1234.5, 1234.5, 0.2, 1572, CURDATE(), 12345, 'EGP',
                                                                                      'Cache', 'asdadasd', 'Collected',
                                                                                      1234, NULL, FALSE);

