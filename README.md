# DAI Lab: SMTP Prank Campaign

## Project Description

This project implements a TCP client application in Java for orchestrating an email prank campaign using the SMTP protocol. The client allows users to configure victim email addresses, email messages, and the number of prank groups. The implemented client interacts with a mock SMTP server for testing purposes.

## Setting up Mock SMTP Server

To facilitate testing without sending real emails, a mock SMTP server is used. Follow these steps to set up the mock server:

1. [Install Docker](https://docs.docker.com/get-docker/) on your machine if not already installed.

2. Run the following Docker command to start the MailDev mock SMTP server:

   ```bash
   docker run -d -p 1080:1080 -p 1025:1025 maildev/maildev
   ```

3. Access the MailDev Web interface at [http://localhost:1080](http://localhost:1080) to view received emails.

## Configuring and Running Prank Campaign

To configure and run the prank campaign, follow these steps:

1. Clone the repository somewhere on your disk:

   ```bash
   git clone <repository_url>
   ```

2. Navigate to the project directory:

   ```bash
   cd smtpclient
   ```

3. Edit the configuration files:
   - Edit `victims` to include a list of victim email addresses.
   - Edit `messages` to define email messages, following the specified format :
     - "MSG_START" to define the start of a new message  
     - "MSG_END" to define the end of the message  
     - "subject :" to define the subject of the email  
     - "body :" to define the body of the email
   

   There can't be any blank lines between MSG_START and MSG_END.
   Example : 
   ```
   MSG_START
   subject : email subject
   body : email body
   still in email body
   MSG_END
   ```

4. Compile and run the program:
   
   ```bash
   mvn clean package
   java -jar .\target\smtpclient-1.jar victims messages <nbGroups>
   ```

5. Observe the output in the console, and check the MailDev Web interface (`http://localhost:1080`) for received prank emails.

## Implementation Overview

The implementation consists of the following key components:

- **VictimsFinder:** Reads and validates victim email addresses from a file, generating random groups for the prank campaign.

- **MessageSelector:** Reads email messages from a file, allowing random selection of messages for the prank emails.

- **EmailSender:** Establishes a connection to the SMTP server and sends prank emails to configured groups using the selected messages.

- **Message:** Represents an email message with a subject and body.

The interactions with the mock SMTP server follow the SMTP protocol, including EHLO, MAIL FROM, RCPT TO, DATA, and QUIT commands.

## Example Dialogue

1. **VictimsFinder:** Reads victim email addresses and generates random groups.

2. **MessageSelector:** Reads email messages, allowing random selection.

3. **EmailSender:** Establishes a connection to the SMTP server, sends prank emails to victim groups using selected messages.

Example Console Output:
```
Group 1 pranked :
-----------------
email.one@heig-vd.ch
asdfh@eeeaaaaa.com
asdsdvgsvbfh@eeee.com
emailtwo@heig-vd.ch

Subject : hello
Body : hello i am a legitimate email sent by a legitimate sender

Group 2 pranked :
-----------------
...
```