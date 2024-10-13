# CarParts Project
This GitHub repository contains the source code of an enterprise management software distributed on a monolithic architecture.
#### Built with 
[![My Skills](https://skillicons.dev/icons?i=java,spring,mysql,redis,aws,docker&theme=light)](https://skillicons.dev)

# General Architecture
![GeneralArchitecture](https://github.com/lixegas/carparts-be/blob/f994e0ca4c3f6e2c8ad06863c813b39b5edb028a/GeneralArchitecture.png)

The application follows a monolithic architecture, where all modules and functionalities are integrated into a single application. Internal communication between components ensures fast and direct interaction within the system.

Communication between components occurs through internal calls within the same application, ensuring smooth and direct interaction between the various parts of the system.

### Main Functionalities

The system handles various business operations related to product, category, and sales management. Below are the main flows.

### Category Management

The application allows for the creation, updating, and deletion of product categories. Each category can have multiple associated products, and changes in categories are automatically reflected in related entities.

## Product Management

When a new product is created, its metadata is stored in MySQL, while product images are uploaded to AWS S3. Users can update or delete products, with changes propagated throughout the system. Redis cache is updated to enhance performance.

### Sales Flow

The sales process covers everything from product selection to inventory updates. After a sale is confirmed, product quantities are updated in MySQL and the Redis cache is refreshed to reflect stock changes.

Sales can also be canceled, restoring inventory quantities and updating related entities.

### Image Upload
![ImageUpload](https://github.com/lixegas/carparts-be/blob/main/ImageUpload.png)

Product images are uploaded and stored in AWS S3. Image metadata is saved in MySQL, and can be retrieved and displayed in the application via a unique url.

## Redis Cache
![Redis Cache](https://github.com/lixegas/carparts-be/blob/main/RedisCache.png)

Redis is used to store frequently accessed data, such as product and category details, improving response times and reducing database load. The cache is updated or invalidated whenever product data changes or sales occur.

## Installation Guide
### Requisites
- Java SDKs 17 and 22
- Docker
### Installation
Clone the Github repo in a directory of your liking.
```
git clone https://github.com/lixegas/carparts-be.git
```


## License
This project is licensed under the MIT license.
