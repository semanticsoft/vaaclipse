if (!document.getBoxObjectFor) {
    document.getBoxObjectFor = function(elem) {
        var obj = new Object;
        var rect = elem.getBoundingClientRect();
        obj.y = rect.top;
        obj.x = rect.left;
        obj.width =Math.abs(rect.right-rect.left);
        obj.height = Math.abs(rect.bottom-rect.top);
        return obj;
    };
}