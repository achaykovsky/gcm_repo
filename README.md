# Global City Map
This project was the main and final part of the Software Engineering course.
We were a team of 4 members.

## Main idea
A system for viewing, editing, and managing city maps.
The system serves both the clients of the company and all the employees.

## Tools 
1. Modeling: Visual Paradigm
2. Programming Language: Java (IDE: Eclipse)
3. GUI Design: JavaFX Scene Builder
4. Database: MySQL

## Project Description
The system has the following screens:

1. Server Connection
2. Registration
3. Maps Catalog
4. Maps Editor Tool
5. Control Panel
6. Purchase Window
7. Client Details
8. Statistics
9. Messages Inbox

### Server Connection
Connecting to the server

![GCM Server](https://user-images.githubusercontent.com/49001453/102250746-98c2e900-3f0c-11eb-8948-e43219db2f2a.png)

### Registration Window
The Catalog can be viewed by everyone, but if the user wants to see the maps - the user should register to the system,
and become a client of the company.

![Registration form](https://user-images.githubusercontent.com/49001453/102254846-e2620280-3f11-11eb-9724-d6a3ec864a0b.PNG)

These details can be changed later in the personal area.

### Maps Catalog
The catalogue has the list of all the sites and the cities that exist of the maps.

The user can search all that by sites, cities or by keywords that describe the map.

![Main](https://user-images.githubusercontent.com/49001453/102257689-bb0d3480-3f15-11eb-9b70-27183751973f.png)

### Maps Editor Tool
This window made from 2 tabs:

**1.** Editing the city properties and making new travel routes that go through all the sites 

**2.** Editing the properties of the each map and each site on the map.

#### 1. Editor Tool - Route View

![Editor Tool - Route View](https://user-images.githubusercontent.com/49001453/102719809-ea96b500-42f8-11eb-911c-7fd2e7431caa.PNG)

To add a new city, or a new route in it, the user can simply choose the first option on the drop-down menu, and then all the fields are initialized.

The employee can choose an existing city, and then choose the route that the worker would like to change.

From the drop-down menu, the employee can choose a site and add it to the route, that can be viewed on the right side of the window.

#### 2. Editor Tool - Map View

![Editor Tool - Map View](https://user-images.githubusercontent.com/49001453/102719795-dd79c600-42f8-11eb-848b-1b252e7d220c.PNG)

To add a new map, or a new site the user can simply choose the first option on the drop-down menu, and then all the fields are initialized.

After a map have been chosen, all the details are loaded to the GUI and can be edited.

On the top right side, the employee can add a site to the map.

Below that, they can edit the details of the site, save it or delete it entirely.

### Control Panel

![Control Panel](https://user-images.githubusercontent.com/49001453/103303781-89b16000-4a0f-11eb-9bb2-5dfe13225876.png)

In this window the the managers and the CEO can see the list of all the clients.

For each chosen client it is possible to see all their personal data, as it explained in the profile window (see below).

Additionally, from this window they can reach the statistics window, inbox and maps editing.

### Purchase Window

![Maps Purchase](https://user-images.githubusercontent.com/49001453/103176523-1d9df300-487b-11eb-8e3f-4afefaa3037d.PNG)

On the left side, the user needs to choose the city, from which he wants to purchase the map.

After that, after the city is chosen, should be chosen the map, and choose between two types of subscription:

**1.** Short term description - Buying and viewing the map at the moment (can't be used in the future).

**2.** Long term subscription - Allowing the user to purchase the map for a specific period: 1 month/3 months/ 6 months.

### My Profile

This window consists from three tabs:

**1.** Personal information
**2.** Payment Details
**3.** Purchases


#### 1. Personal information

![My Profile - Personal Information](https://user-images.githubusercontent.com/49001453/103177015-09f48b80-487f-11eb-9cac-4d662e2bedda.PNG)

On this tab, the user can update the information that have been entered in the registration stage.
The only property that can't be changed is the user name, which is unique.

#### 2. Payment Details

![My Profile - Payment Details](https://user-images.githubusercontent.com/49001453/103177014-095bf500-487f-11eb-9b4b-a82d9ae895b6.PNG)

On this tab, the user can update the payment information that have been entered in the registration stage.

#### 3. Purchases

![My Profile - Purchases](https://user-images.githubusercontent.com/49001453/103303780-8918c980-4a0f-11eb-84cd-0e8e83f2215f.png)

On this tab, the user can see the list of the maps that have been purchased.
Also, from this window the user can reach all the purchases that didn't expire yet.
Each purchase in the list that didn't expire yet, can be downloaded as a file and viewed.
Every purchase can be renewed: after clicking the button, it will renew the chosen map with the same time terms, and will give an automatic discount for the renewal.

Three days before the expiration of a map, the user will recieve a notification to the e-mail:
![renewal of purchase](https://user-images.githubusercontent.com/49001453/103301549-53bdad00-4a0a-11eb-86a7-f2e2b60124c2.png)


