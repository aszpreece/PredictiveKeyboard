# Word Prediction

We want to use a tree data structure to which will store words for efficient word prediction.
We should be able to query the tree by providing a prefix, e.g. "telep", and it should return
a "prediction" for the full word we intend to write, e.g. "telephone".

A naive algorithm, which iterates over a list of words, checking each word to see if it starts with the provided prefix,
runs in linear time. Using a tree, we can get an algorithm that runs in logarithmic time.

## Learning objectives

* Trees
* Optional
* Representing data types and data structures in Java
* Avoidance of null as a programming technique
* Assert

For full marks, instead of using null, use other techniques such as `Optional`, empty (non-null) tables, etc. Extra credit will be given to correct, meaningful use of `assert`.

## Procedure

* Start with a copy of the skeleton code for this task, which can be found [here](./skeleton-code).
* Modify the `DictionaryTree` class.
    * You can write helper methods.
    * You must not modify the signatures of the methods provided, as we will be using automated marking which will assume these methods have been implemented.
    * If you have not implemented a method, testing will not be used on this method.
* You need to add testing code to convince yourself that your code works.
* You can use the `CLI` to perform some testing by hand to quickly check if you are heading in the right direction, check for bugs, etc.
* You are highly encouraged to write unit tests for your code. There are a few sample unit tests in the `DictionaryTreeTests` class, which can be run with the `run-test-suite.sh` script. These will help to ensure you have implemented your solution correctly, and you may get credit if these are done properly.
    * To avoid duplicate large files, we have used symbolic links to point to `lib/junit.jar`. If you have problems with these symbolic links, just create a copy of `lib/junit.jar` in place of the symbolic link.

## Methods to implement

* `size`: returns the number of nodes in the tree.
* `height`: returns the height of the tree. Since the tree is never empty, height should always be non-negative.
* `maximumBranching`: each node has a number of children - `maximumBranching` should return the maximum number of children in a held by any node.
* `longestWord`: returns the longest word stored in this tree.
* `numLeaves`: returns the number of leaves in this tree, i.e. the number of words in this tree which are not prefixes of any other word.
* `contains`: returns true if the given word is held in this tree, and false otherwise.
* `allWords`: returns all words held in this tree.
* `insert`: inserts the given word into this tree.
* `remove`: removes the given word from this tree. If the word is not already in the tree, nothing should change. If the word is in the tree, then `contains(word)` should return true before invoking `remove(word)` and return false after.
* `predict`: given a prefix, this method should return a word in this tree that starts with this prefix.

## Challenges

The following challenges can be implemented to obtain 75%+.
As with previous assignments, the **challenges should only be attempted once all other questions have been solved**.

### Frequency-based prediction

The words in `word-popularity.txt` are ordered such that word on the nth line is the nth most common word to appear in English texts.
Implement the `insert(String word, int popularity)` method, which inserts the given word with the given popularity (a higher value of `popularity` means the word is more popular than a word with a lower value of `popularity`).
Modify the `predict` method so that instead of predicting *any* word which starts with the given prefix, it returns the `n` most popular words with the given prefix.
A word's popularity is measured by its line number in `word-popularity.txt` - the lower the line number the higher the popularity.
For example, `predict("ph", 5)` should return the list `[phone, photo, photos, phones, physical]`.

What are the advantages/disadvantages of using a tree for predicting multiple words with ranked popularities?

### Fold

Several methods have a similar implementation pattern:
* Compute some value for each node in the tree
* For each node, compare this value the values obtained for the children
* Calculate a final result

This is a type of fold, where we provide a function, which is used to fold the child nodes and is then used to calculate a final result with the current node and the folded child nodes as input.

Your task is to implement the `fold` function and the reimplement the following methods by passing the appropriate function to `fold`:
* `size`
* `height`
* `maximumBranching`
* `numLeaves`

You should replace your existing implementation for each method with the new implementation (rather than writing a new method).

You may find it useful to recall the lecture on [higher-order functions with immutable lists](https://git.cs.bham.ac.uk/mhe/SWW-2017-2018/tree/master/LectureNotesAndCode/ImmutableLists-higher-order).
