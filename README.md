# Scaffold-Master
## MurckoTest

__Class MurckoTest shows the funktions of the CDK MurckoFragmenter__
  
  A MOL file is loaded from the MOL_Files folder and processed with the MurckoFragmenter. The following molecules are displayed in the opened window:
  * Original: The unchanged molecule
  * Fragments: The fragments generated by the MurckoFragmenter
  * Rings: The rings generated by the MurckoFragmenter
  * Frameworks: The frameworks generated by the MurckoFragmenter
  
  Examples:
  * *Test1* shows that linkers are not further decomposed
  * *Test3* shows that ring systems are separated from one another
  * *Test6* shows that side chains of the ring systems are removed and ring systems are not further broken down into individual rings
