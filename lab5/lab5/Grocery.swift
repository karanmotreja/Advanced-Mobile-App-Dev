//
//  Grocery.swift
//  lab5
//
//  Created by Karan Motreja on 3/19/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import Foundation
import RealmSwift

class Grocery: Object {
    @objc dynamic var name = ""
    @objc dynamic var bought = false
}
