import pandas as pd
from sklearn.naive_bayes import BernoulliNB
from sklearn.metrics import accuracy_score
model = BernoulliNB()

#Read data
mnist_train = pd.read_csv('C:\RPi Server\!Electrical Engineering\!Semester 1\Artificial Intelligence\mnist_train_th128.csv')
mnist_test = pd.read_csv('C:\RPi Server\!Electrical Engineering\!Semester 1\Artificial Intelligence\mnist_test_th128.csv')

data_training = mnist_train[0:3001]
data_testing = mnist_test[0:51]

#print(data_training)
#print(data_testing)

#Removing label
data_training_new = data_training.drop(columns=['label'])
data_testing_new = data_testing.drop(columns=['label'])

#print(data_training_new)
#print(data_testing_new)

data_training_out = data_training['label']
data_testing_out =  data_testing['label']
#print(data_training_out)
#print(data_testing_out)

model.fit(data_training_new,data_training_out)
expected = data_testing_out
predicted = model.predict(data_testing_new)
print(predicted)
print("Error ratio dari 50 percobaan adalah : ",(100-(accuracy_score(expected,predicted)*100)),"%")