import axios from "axios"

axios.get("https://fakestoreapi.com/products")
    .then(res=> {
        console.log(res.data)
        res.data.forEach(item=>{
            let object={
                "title" : item.title,
                "price" : item.price,
                "category" : item.category,
                "description" : item.description.substring(0,255),
                "image" : item.image,
                "rating" : item.rating.rate,
                "ratingCount": item.rating.count,
                "onSale": false
            }

             axios.post("http://localhost:8080/api/items", object)
            .then(res=> console.log(res))
            .catch(err=> console.log(err))
        })
    })
    .catch(err=> console.log(err))


    let user={
        "firstName" : "root",
        "lastName" : "root",
        "email" : "root@root.com",
        "address" : "root address",
        "password" : "rootroot",
        "confirmPassword" : "rootroot",
        "isAdmin" : true
    }

axios.post("http://localhost:8080/api/user", user)
    .then(res=>console.log(res))
    .catch(err=> console.log(err))