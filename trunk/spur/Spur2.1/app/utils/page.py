DEFAULT_PAGE_SIZE = 20

class Page(object):
    def __init__(self, object_list, page_number, total_count, per_page = DEFAULT_PAGE_SIZE):
        self.object_list = object_list
        self.page_number = page_number
        self.total_count = total_count
        self.per_page = per_page
        self._num_pages = None

    def __repr__(self):
        return '<Page %s of %s>' % (self.page_number, self.total_count)
    
    @property
    def num_pages(self):
        if self._num_pages is None:
            hits = self.total_count - 1
            if hits < 1:
                hits = 0
            else:
                self._num_pages = hits // self.per_page + 1
        return self._num_pages
    
    @property
    def page_range(self):
        return range(1, self.num_pages + 1)
    
    @property
    def has_next(self):
        return self.page_number < self.num_pages
    
    @property
    def has_previous(self):
        return self.page_number > 1
    
    @property
    def next_page_number(self):
        return self.page_number + 1

    @property
    def previous_page_number(self):
        return self.page_number - 1
    
    @property
    def start_index(self):
        """
        Returns the 1-based index of the first object on this page,
        relative to total objects in the paginator.
        """
        return (self.per_page * (self.page_number - 1)) + 1
    
    @property
    def end_index(self):
        """
        Returns the 1-based index of the last object on this page,
        relative to total objects found (hits).
        """
        if self.page_number == self.num_pages:
            return self.total_count
        return self.page_number * self.per_page

    
if __name__ == '__main__':
    page = Page([1, 2, 3], 2, 10, 2)
    print page.next_page_number