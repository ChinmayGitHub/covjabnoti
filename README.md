# covjabnoti - coronavirus vaccine tracker

steps to activate the bot on your WhatsApp
1. Add this phone number +34644204756 into your Phone Contacts. (Name it as you wish)
2. Send this message "I allow callmebot to send me messages" to the new Contact created (using WhatsApp)
3. Wait until you receive the message "API Activated for your phone number. Your APIKEY is xxxx" from the bot. 
4. send me that msg
5. you'll get one more msg from that no. with a link, ignore it.. and don't click on the link.
6. also send me the district name to which you want alerts

steps to subscribe to a particular district
1. start the app either locally using docker-compose or k8 (if cloud, make sure ur VM is in india)
2. Open http://localhost:8080/api/swagger-ui.html#/
3. POST 
    { "district_id" : "97",
    "name" : "chinmay",
    "phone_no" : "+91737...596",
    "api_key" : "86xxx52",
    "age_slot" : "18" }
4. Once any slots are available in the district, you'll get a WhatsApp msg.
