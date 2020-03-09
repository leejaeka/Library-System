User manual
leejaeka 1003661272

- Run the program (main method in Main())
- When asked for selection choose one of the option
- binded by '()'
- For ex. "Please identify yourself (1) Member (2) Staff (3) Exit Program :"
- Then type '1' or '2'  or '3' and press enter to proceed
- Note that entering anything other than given options result in asking for
- 'retry' or take infinite inputs until a satisfactory option is given.
- When asked for string such as title of book simply type out the name of the book
- and press enter. You do not have to worry about spacing in the front and back as
- i've used trim()

- Note that in main method there's a block of code in the beginning. Read its
- comment and uncomment out if appropriate. To visually see this
- (without having it commented out) Run program -> (2) staff -> (5) Check Library Status

- MAIN SCREEN
    -Welcome to our library!!
     Enter the date(yyyy mm dd):
    - Here please enter the CURRENT date

- MEMBER
    -Once you have selected (1) Member, you will have to enter your username
    -Program will automatically recognize your username if it's been registered already
    - and log you in
    -Otherwise, you will still be logged in the same except system will automatically register your
    - account with entered username for future usage

- POST LOGIN SCREEN (MEMBER)
    -Once login completes, member will be greeted by five different options and their status. They can check their
        status again once they logout login again or (2) check status.
        (Also this is where like at this moment the program will automatically adjust member's balance
          according to their overdue records ( a function in Member called automaticSetBalance())
        ->(1) Search/Checkout : search the library for a book and checkout if available
                                if not available, waitlisting is an option. Returns to post login
                                screen once task all done.
        ->(2) Check Status : can check status of checked outed books(overdue?nearly overdue?)
                            , Waitlist (available?) and balance. Returns to post login screen
        ->(3) Return/extend : can return a checked out book or extend a book if haven't extended once
                              already. Also can change extension time in Library function, CHECK_OUT TIME.
                              Returns to post login screen.
        ->(4) Waitlist : Can check if book on my waitlist is now available, if available can check out
                         and can also drop waitlist.
        ->(0) Logout : Exit to MAIN SCREEN

-POST LOGIN SCREEN (STAFF)
    -Once login completes, staff will be greeted by six different options
            ->(1) Register new book : register a new book with a title. If title has conflict with already
                                      existing book title, ask if want to update the book since all that
                                      is wanted is probably just change number of copies. But other fields are
                                      open for update as well. Returns to post login screen
            ->(2) Update a book : can modify description/title/number of copies available/branch of any existing book.
                                  If book doesn't exist with given title input,
                                  Returns to post login screen
            ->(3) Pay penalties : can pay penalties for any member. If can't find member, return to post login screen
                                  Returns to post login screen.
            ->(4) Adjust Balance : Can adjust any user's balance. If can't find member, return to post login screen
                                    Small note: if can't find member, will still ask for amount but same result.
                                   Returns to post login screen
            ->(5) Check Library Status : can check all members of library and all books and its information
                                          Returns to post login screen
            ->(0) Logout : Exit to MAIN SCREEN

- Note that only in MAIN SCREEN user can completely 'EXIT' the program. Otherwise users have to log out first.
- Note that restarting the program loses all the information updated.

A little about my code..
Book is book, library is library(have books& all its members), time is for dues on book
(used once a book is checkedout), Member is for members
(what they can do and what they are/have) and staff is for staff(Managing the library and members).
Main is where everything is set up. (Default members, books)+Pre login screen+ exiting program
Thank you for your time : )