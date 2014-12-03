/**
 * Created by Andreea on 12/3/2014.
 */
public class Todo {
    /*
        STATUS={IN_PROGRESS, DONE, N/A}
        PRIORITY={HIGH, MEDIUM, LOW}

        1. Contextual menu:
            when a user is not logged in, in the menu will be displayed only the Login button;
            when the user is logged in, in the menu will be displayed the following: My Profile, My Pools
            PRIORITY: MEDIUM
            STATUS: IN_PROGRESS

        2. Delete from menu Settings option
            PRIORITY: MEDIUM
            STATUS: IN_PROGRESS

        3. Make menu options work on any page
            PRIORITY: HIGH
            STATUS: IN_PROGRESS


        4. Create Pool:
            Prompt user when tries to create a pool without one of the following:
                - location source
                - location destination
            Set a meaningful error message (display it as a popup/dialog when the user presses button create pool)
            PRIORITY: HIGH
            STATUS: IN_PROGRESS

        5. Search Pool:
            Prompt user when tries to search a pool without one of the following:
                - location source
                - location destination
            Set a meaningful error message (display it as a popup/dialog when the user presses button search)
            PRIORITY: HIGH
            STATUS: IN_PROGRESS

        6. Fix Bugs in MyPools
            - after a driver created a pool, it is not shown in MyPools page (when it is the first time on page)
            PRIORITY: HIGH
            STATUS: IN_PROGRESS

        7a. Add more information about the Matching Pools: is the pool weekly?!
            PRIORITY: HIGH
            STATUS: IN_PROGRESS

        7b. Fix bug in Matching Pools(if screen goes of and then on, the pools are duplicated)
            STATUS: IN_PROGRESS
            PRIORITY: MEDIUM

        7c. Matching Pools: redirect to MyPools after user presses join
            STATUS: IN_PROGRESS
            PRIORITY: MEDIUM

        7. Redesign Matching Pools (move button next to text; now it is below text -> can be done
           with RelativeLayout instead of LinearLayout
            STATUS: IN_PROGRESS
            PRIORITY: LOW

        8. Server Code: Search for routes using the coordinates also
            (tutorial: https://parse.com/docs/android_guide#geo)
            STATUS: IN_PROGRESS
            PRIORITY: HIGH

        9. Server Code: New Query to search for weekly pools (it should search only by time
            and GeoLocation Points)
            STATUS: IN_PROGRESS
            PRIORITY: HIGH

         10. Server Code: After adding passenger to pool, decrease number of available seats
            STATUS: IN_PROGRESS
            PRIORITY: HIGH
     */
}
