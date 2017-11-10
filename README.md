<h1>Closest Pair Problem in n-dimension</h2>
<a href="#tartota"> Analysis The Asymptotical Running Time of The Algorithm </a>

<a href="#usageInst"> Usage Instructions </a>

<a href="#limitations"> Limitations </a>

<a href="#comments"> Comments </a>

<a name="tartota"></a>
# Analysis and The Asymptotical Running Time of The Algorithm
Two algorithms are presented in this project to solve n-dimensional closest pair problem. First one is the naive brute-force algorithm and the second one is a divide and conquer algorithm with a lookup table created by myself.

* <b>Naive Brute-Force Algorithm:</b> <br/>
The main idea here is to calculate distances among each point in order to find the minimum distance and closest pairs. For a set of n points this takes "n<sup>2</sup>-n" calculations for one dimension. In "d" dimensions this will take "d*(n<sup>2</sup>-n)" operations. This solves to O(d*n<sup>2</sup>) and for a constant "d" this solves to O(n<sup>2</sup>).

* <b>Divide and Conquer Algorithm with Lookup Table</b>: <br/>
Aim of this algorithm is to improve naive brute-force algorithm by reducing the number of point comparisons. The main idea here is to sort points according to some coordinate axis then divide points into smaller groups recursively and calculate distances of closer points, then find the minimum distance and closest pair. For smaller dimensions it is easier to develop an algorithm but things got complicated when dimension is greater.

1) First step of the divide and conquer algorithm is to sort the points by some coordinate axis so that closer points will be ordered. 
2) Then middle point is found and points will be divided into two halves by this middle point. After that recursively this division by middle point continues and minimum distances and closest pairs is calculated at both sides and then the minimum of these two values is decided.
3) There is another issue to consider here since we are dividing the points into two sections and doing calculations in those sections we are not able to calculate distances between points that are close to the middle point but lie in the opposite sections. So, after we calculate the minimum distances in both halves and get the minimum of these two, lets say K, we find points that are closer to the middle point than K.
4) After finding the points mentioned in step 3, distances among these points are calculated. If a distance less than K is found then that value will be returned as minimum, if not then nothing changes.

    For smaller dimensions, above 4 steps may be sufficient to find the closest pair however this 4 steps don't work for higher dimensions. In order to adjust this algorithm to work for any dimension a for loop is created. This for loop runs for every coordinate, in this for loop, at first the points are sorted by a coordinate and then the above 4 steps are iterated. This for loop helps finding the closest pair succesfully, however this for loop results in too many repeated distance calculations in different iterations of the loop. This completely contradicts to our aim of developing this algorithm. By introducing a lookup table to store calculated distances between points, this problem is solved. Whenever a new distance between two points is calculated, that value is added to the lookup table. Whenever a distance between two points is needed firstly lookup table is checked if the value exists then no more calculation is needed if it doesn't exist then the distance is calculated and lookup table is updated. With the help of this lookup table no more than one distance calculation for any two points is ensured. So, the worst case scenario would be calculating distances among all points which is the same as naive brute-force algorithm. Thus average case is better than the brute-force algorithm.
    
    <b>Time-Complexity Calculation:</b><br/> 
    Since divide and conquer algorithm with lookup table is used for every dimension, distances between closer pairs are calculated and stored in lookup table thus for every other dimension problem takes place in one lower dimension. 
    Furthermore the calculations made in steps 3 and 4 should be taken into account. Let's say S is a set where inter-point distance is at least K(from step 3). Then there can be sets like S in both sides of middle point. Again let's say C is a d-cube with edges 2K long centered at some Q. Let's say L is the set of points in C. So if we place d-spheres with radius K/2 around each point of L, no two d-spheres can intersect since inter-point distance is at least K. The volume of cube is (2K)<sup>d</sup> and the volume of a d-sphere is (1/c<sub>d</sub>).(K/2)<sup>d</sup>. 'c<sub>d</sub>' is a constant number that depends on d. Thus at most c<sub>d</sub>.4<sup>d</sup> d-sphere can be placed inside a d-cube and that number becomes constant with a constant d. So in the worst case a point very close to the middle point in the left side of the middle point can only be compared to the points in some L. That will be 2.O((n/2).(1)) and that solves to O(n).
    The recurrence for the final algorithm is:<br/>
    T(n,d) = 2T(n/2,d) + O(n, d-1) + O(n)<br/>
    T(n,d) = 2T(n/2,d) + O(dn(logn)<sup>d-2</sup>) + O(n)<br/>
    T(n,d) = O(dn(logn)<sup>d-1</sup>) <br/>
    T(n,d) = O(n(logn)<sup>d-1</sup>) for constant d<br/>
           
<a name="usageInst"></a>
# Usage Instructions
<b>From command line:</b>
* In the beginning, classpath should be set to the directory where closestpair, InputTestFiles and OutputTestFiles are located.<br/>
Windows CMD: <i>set CLASSPATH = %classpath%;/some/directory/</i><br/>
Unix Terminal: <i>export CLASSPATH = $CLASSPATH:/some/directory/</i><br/>
* Navigate to the closestpair folder in which the source files are located and compile the java source files.<br/>
Command: <i>javac *.java</i>

* There are 3 different configuration to run this program:<br/>
    Command: <i>java closestpair.Main [|1|2] [|input file location] [|output file location] </i><br/>
    1) <i>java closestpair.Main</i><br/>
                * If program is run without command line arguments then the divide-and-conquer algorithm is used. Input file location is taken as "../InputTestFiles/sample_input_91_732.tsv" and output file will be at "../OutputTestFiles/sample_input_91_732.txt".
    
    2) <i>java closestpair.Main 1 "/some/directory/input_file_location" "/some/directory/output_file_location"</i><br/>
                * First command line argument decides which algorithm will be used to solve the problem. If the first argument is 1 then the naive brute-force algorithm will be used. Second argument will be the input location and the third will be the output location. <br/>
                
    3) <i>java closestpair.Main 2 "/some/directory/input_file_location" "/some/directory/output_file_location"</i> <br/>
                * If the first argument is 2, then the divide-and-conquer algorithm will be used. Second argument will be the input location and the third will be the output location. <br/>

<a name="limitations"></a>
# Limitations
* Input text file should contain one point per line. For every line, coordinate values should be separated by "\t". For every point, input file should contain exactly same number of coordinates.
* There should be at least two points in input file.
* If program will be run without command line arguments then ../InputTestFiles/sample_input_91_732.tsv should exists, if it doesn't exist then program crashes.
* There is no restriction for dimension.
* There is no maximum limit for points.
* To run the program with command line arguments there should be exactly two arguments provided. First one should be input file location and the second one should be output file location.

<a name="comments"></a>
# Comments
* Test input files created by myself is located under InputTestFiles folder.
* Test output files created by the program is located under OutputTestFiles folder.

