//
//  GroceryDataController.swift
//  lab5
//
//  Created by Karan Motreja on 3/19/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import Foundation
import RealmSwift

class GroceryDataController {
    var myRealm1 : Realm!
    var groceryData: Results<Grocery>
    {
        get {
            return myRealm1.objects(Grocery.self)
        }
    }
    
    func dbSetup(){
        //initialize the Realm database
        do {
            myRealm1 = try Realm()
        } catch let error {
            print(error.localizedDescription)
        }
        print(Realm.Configuration.defaultConfiguration.fileURL!)
    }
    
    func getGroceries()->[Grocery]{
        return Array(groceryData)
}

    func addItem(newItem:Grocery){
        do {
            try self.myRealm1.write({
                self.myRealm1.add(newItem)
            })
        } catch let error{
            print(error.localizedDescription)
        }
    }
    
    
    func boughtItem(item: Grocery){
        do {
            try self.myRealm1.write ({
                item.bought = !item.bought
            })
        }catch let error{
            print(error.localizedDescription)
        }
    }
    
    
    func deleteItem(item: Grocery){
        do {
            try self.myRealm1.write ({
                self.myRealm1.delete(item)
            })
        } catch let error{
            print(error.localizedDescription)
        }
    }
    
}
