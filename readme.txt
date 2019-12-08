Run npm install to install the dependencies
Start the node server
Input Parameters -> Sleep,sugarCountBefore,sugarCountAfter,totalCaloriesIntake,totalCaloriesBurnt.
Call the route http://localhost:5600/health-check with the parameter values which are prescribed in a post .
It will predict how healthy your routine was in the day.

We have used Microsoft Azure Machine Learning Studio(classic)
ML used for the project is Two-Class Boosted Decision Tree.
The aim of the project is to determine the diabetic level of the user based the trained model of normal and diabetic people.
In the app when the routine is entered with Blood sugar values, calorie intake and other Parameters described, the ML will predict the probablility of the user's lifestyle being more prone to diabetes.
We have suggestions to help the user have a better lifestyle and avoid risks of being prone to diabetes.