List product api:
Get request : localhost:8080/products
Initially 10 records will be loaded, this can be changed from application.properties file.
If you want to jump to the specific page pass parameter as localhost:8080/products?pageNo=1

User Can post the product in same way using post request:
localhost:8080/products.
User can post multiple products at once. It is mandatory to provide user id as well to identify which user has posted the product.
Sample : 
{
	"products":
	[	
		{
			"productName": "clock",
			"price": 9,
			"description": "clock"
		}
	],
	"userId":1
}
This information will get stored in ecom_product.
On top of that one more entry will be inserted ecom_product_seller

User can add item to the cart using below post request:
localhost:8080/cart
Here use needs to pass userid, list of productIds, and list of quantities needs to be passed.
Example:
{
	"userId":1,
	"productId":[1,2,3,3],
	"quantity":[2,1,1,9]
}
product id needs to be present into ecom_product table if it doesn't exist then products will not be added to the cart.
If the duplicate product exists in the cart in the same or different input then quantity will get increased.
This will be stored in ecom_user_cart table.

Order will be placed using below post request:
localhost:8080/order?userId=1
here based on user id order will get placed, if no such cart with user exist then there will be error.
we have mocked payment service if it fails then order will not be placed.
upon successful execution of order, cart will be cleared.

For future enhancement we have introduced flyway db migration.
So across the enviornment database versioning can be maintained.