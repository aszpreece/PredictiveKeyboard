## Solution

git: https://git.cs.bham.ac.uk/tap747/WordPopularity.git

I used a mix of iterative and recursive approaches to solve the task. This was so I could show off BFS traversal and that I am able to use both techniques. It is quite a contensious topic as to which style is better, recursive often being more elegant but at the risk of decreased performance in some enviroments and stack overflows. Recursive functions are also harder to maintain as they are often more difficult to understand.

To signal the end of a word, the node will have an optional type called popularity. If this has a value then it signals the end of a word, if it is empty it is just a regular node. An alternative approach could have been to have a special symbol or node that would signal the end of a word that would be the child of the last letter of a word, but this would have taken up more memory and would have made popularity based searching harder to implement.

I have created a few helper methods and classes to assist. 'Word' is a local class that contains a word and its given popularity. It is only ever used inside this class, so there is no need for it to be public or for it to use getter and setter methods as it privacy means I cannot break the overall API of my DictionaryTree class.
wordToString takes a list of words and converts it to a list of strings. This is just so the words can be ordered based on popularity. Word implements the comparable interface so the list can be sorted using Comparator.naturalOrder.

Words with a higher popularity number are regarded as more popular. Minimum popularity is Integer.MIN_VALUE. This is also the default popularity value.

The 4 methods that were said to be implemented with fold were properly implemented with fold using appropriate inner bi function classes. These could be done on a single line of code therefore making the inner class an anonymous inner class but for the sake of readability I didn't do this.

##Question

The advantage of using trees for words with ranked popularities is that you can still retain the speed of traversal that trees offer over the O(n) complexity of linear word prediction algorithms.
However the disadvantage is that once the tree has been traversed up until the prefix, all words then starting with that prefix must be found and returned in order to find the n most popular words starting with that prefix.This is because the words are not stored based on their popularity. This could significantly increase search times. This problem could be solved in a different data structure where words are stored in order of their popularity somehow, allowing for only the entries for the n most popular nodes to be traversed.

##JUnit
I have written multiple test cases that all pass in the DictionaryTreeTests.java file. They use some more advanced features of JUnit such as assertIterableEquals and the @Test(expected=...) annotation.
