# required libraries
import pandas as pd
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.metrics import accuracy_score
from sklearn.neural_network import MLPClassifier

# read the dataset
data = pd.read_csv('Iris.csv')
print(data.head())
print('\n\nColumn Names\n\n')
print(data.columns)

#label encode the target variable
encode = LabelEncoder()
data.species = encode.fit_transform(data.species)#Normalize the string data in species to number
print(data.head())
# train-test-split   
train , test = train_test_split(data,test_size=0.2,random_state=0)#Split train-test
print('shape of training data : ',train.shape)
print('shape of testing data',test.shape)

# seperate the target and independent variable
train_x = train.drop(columns=['species'],axis=1)#erase species column from input
train_y = train['species']#making species column as output

test_x = test.drop(columns=['species'],axis=1)
test_y = test['species']
# create the object of the model
#model = LogisticRegression()
#model.fit(train_x,train_y)
clf = MLPClassifier(activation='relu',solver='sgd',learning_rate="adaptive",learning_rate_init=0.01,random_state=1,hidden_layer_sizes=(256),max_iter=2000)
clf.fit(train_x,train_y)
predict = clf.predict(test_x)
print('Predicted Values on Test Data',encode.inverse_transform(predict))
print('\n\nAccuracy Score on test data : \n\n')
print(accuracy_score(test_y,predict))

#read data using pd.read_csv
#convert data label to numerical using LabelEncoder.fit_transform
#split data training and test using traintestsplit
#split input and output
#model AI

