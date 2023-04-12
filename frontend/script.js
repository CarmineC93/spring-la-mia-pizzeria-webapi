// GLOBAL VARIABLES
const PIZZAS_URL = 'http://localhost:8080/api/pizzas';
const contentElement = document.getElementById('content');

// API REQUESTS
const getAllPizzas = async () => {
                            // default = GET
    const response = await fetch(PIZZAS_URL);
    return response;
};

const loadData = async() =>{
    //call api request
    const response = await getAllPizzas();
    console.log(response);
    if(response.ok){
        //get data
        const data = await response.json();
        console.log(data);

    }else{
        console.log("error");
    }
    //print content into the DOM
    
};

loadData();

