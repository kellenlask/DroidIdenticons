# DroidIdenticons
A java class that generates Identicons from any java object by using the Object.hashCode() method. This class leverages Android graphics libraries, but it would be easy to adapt to other languages and environments.

If you want your object to be properly represented, it must implement its own hashCode() override. The default Object hashCode method is to use the Object's memory address -- not ideal. 

Issues: 
  - Identicons of a large enough scale will show obvious repetition
  - Hash codes are not guaranteed to be unique, and so two objects can share an Identicon
  - Some hash codes may generate no Identicon (rare)
  
Future Changes Should Include: 
  - A better algorithm that will take into account empty images and apply some consistent transformation to ensure an image
  - Lower Big O
  
