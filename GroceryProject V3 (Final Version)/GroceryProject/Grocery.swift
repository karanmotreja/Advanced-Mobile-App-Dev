//
//  Grocery.swift
//  GroceryProject
//
//  Created by Karan Motreja on 3/10/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import Foundation
import UIKit
import UserNotifications

class Grocery: NSObject, NSCoding
{
    
    //request authorization for local notifications
   var notification: UILocalNotification

    
    var name: String
    var time: NSDate
    
    // persistence
    static let DocumentsDirectory = FileManager().urls(for: .documentDirectory, in: .userDomainMask).first!
    static let ArchiveURL = DocumentsDirectory.appendingPathComponent("reminders")
    
    struct PropertyKey
    {
        static let nameKey = "name"
        static let timeKey = "time"
        static let notificationKey = "notification"
    }
    
    init(name: String, time: NSDate, notification: UILocalNotification)
    {
        self.name = name
        self.time = time
        self.notification = notification
        
        super.init()
        
    }
    
    deinit {
      UIApplication.shared.cancelLocalNotification(self.notification)
        
    }
    
    func encode(with aCoder: NSCoder) {
        aCoder.encode(name, forKey: PropertyKey.nameKey)
        aCoder.encode(time, forKey: PropertyKey.timeKey)
        aCoder.encode(notification, forKey: PropertyKey.notificationKey)
    }
    
    required convenience init(coder aDecoder: NSCoder)
    {
        let name = aDecoder.decodeObject(forKey: PropertyKey.nameKey) as! String
        let time = aDecoder.decodeObject(forKey: PropertyKey.timeKey) as! NSDate
        let notification = aDecoder.decodeObject(forKey: PropertyKey.notificationKey) as! UILocalNotification
    
        self.init(name: name, time: time, notification: notification)
    
    }
    
    
}
