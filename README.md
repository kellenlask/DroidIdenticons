# DroidIdenticons
Check out the pictures in the images directory!

A java class that generates Identicons from any java object by using the Object.hashCode() method. This class leverages Android graphics libraries, but it would be easy to adapt to other languages and environments.

If you want a custom object to be properly represented, it should implement its own hashCode() override. The default Object hashCode method returns the Object's memory address -- not ideal. Also included is an example AsynTask to populate Identicon images into an ImageView asynchronously. 

Issues: 
  - Identicons of a large enough scale will show obvious repetition
  - Hash codes are not guaranteed to be unique, and so two objects can share an Identicon
  - Some hash codes may generate no Identicon (rare)
  
Future Changes Should Include: 
  - A better algorithm that will take into account empty images and apply some consistent transformation to ensure an image
  - Lower Big O
  
Current Features: 
  - Dynamic background color
  - Ensures minimum color contrast between background and foreground
  - Horizontal symmetry
  - Dynamic grid size (the image is a grid of squares which can be adjusted) 
  - Dynamic bitmap size (the size of the generated bitmap, independent of pattern) 
