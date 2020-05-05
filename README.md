## SARAVANAN POC TEST ON DEPENDENCY INJECTION WITHOUT SPRING OR ANY CONTAINER

    Idea is to create spring like container but my own customised version. 

#### Added user defined annotations
    
    Singleton (example A & C)
    Prototype (example B )
    Autowired 
    

#### Added user defined exception

    BeanNotFoundException

    
#### How to create Singleton    

    A first = (A) di.getBean(A.class);
    A first = (A) di.getBean("A");
    
#### How to create Prototype    

    ObjectKey ok = new ObjectKey(B.class.getName(),"santhosh");
    B myB = (B) di.getBean(ok);
       or
    B myB = (B) di.getBean("B", "santhosh");
        "B" refers class name to create
        "santhosh"  refers class id for each instance of B
        
#### Limitations

    ~~(1) 
    I need to mute default constructor manually on Singleton classes
            private A() {}
    to avoid accidental creation of multiple instances by developers
    since there is no guarantee that they will always follow my framework~~
    
    (2) Managing object removal to reclaim memory is out of scope
    
    (3) Only one parameter String parameter is accepted for prototype
    
    (4) No major negative scenarios were tested since it is a basic project
    
    ~~(5) Expecting developer to create getInstance in Singleton scope~~ 
    I have commented all the tests of getInstance() from Singleton class
    
    (6) I have used lombak and avoided getter and setter
    
    (7) I have used default access modifier to avoid getter and setter from client side
        To gain better readability
        
    (8) To consider important feature dependency injection during runtime, 
        we have removed concrete private default constructor on Singleton
        
    (9) So impact from 8th point is there is a chance of creating accidental instance objects 
        though it is marked for @Singleton   
        
    
    
            
    
    

    